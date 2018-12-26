package cn.fuelteam;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.ErrorHandler;
import org.springframework.util.SocketUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.UUID;

public class EmbeddedZooKeeper implements SmartLifecycle {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedZooKeeper.class);

    private final int clientPort;

    private boolean autoStartup = true;

    private int phase = 0;

    private volatile Thread zkServerThread;

    private volatile ZooKeeperServerMain zkServer;

    private ErrorHandler errorHandler;

    private boolean daemon = true;

    public EmbeddedZooKeeper() {
        clientPort = SocketUtils.findAvailableTcpPort();
    }

    public EmbeddedZooKeeper(int clientPort, boolean daemon) {
        this.clientPort = clientPort;
        this.daemon = daemon;
    }

    public int getClientPort() {
        return this.clientPort;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    @Override
    public boolean isAutoStartup() {
        return this.autoStartup;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    @Override
    public int getPhase() {
        return this.phase;
    }

    @Override
    public boolean isRunning() {
        return (zkServerThread != null);
    }

    @Override
    public synchronized void start() {
        if (zkServerThread != null) return;
        zkServerThread = new Thread(new ServerRunnable(), "ZooKeeper Server Starter");
        zkServerThread.setDaemon(daemon);
        zkServerThread.start();
    }

    @Override
    public synchronized void stop() {
        if (zkServerThread == null) return;
        try {
            Method shutdown = ZooKeeperServerMain.class.getDeclaredMethod("shutdown");
            shutdown.setAccessible(true);
            shutdown.invoke(zkServer);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        try {
            zkServerThread.join(5000);
            zkServerThread = null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Interrupted while waiting for embedded ZooKeeper to exit");
            zkServerThread = null;
        }
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    private class ServerRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Properties properties = new Properties();
                File file = new File(System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID());
                file.deleteOnExit();
                properties.setProperty("dataDir", file.getAbsolutePath());
                properties.setProperty("clientPort", String.valueOf(clientPort));

                QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();
                quorumPeerConfig.parseProperties(properties);

                zkServer = new ZooKeeperServerMain();
                ServerConfig configuration = new ServerConfig();
                configuration.readFrom(quorumPeerConfig);

                zkServer.runFromConfig(configuration);
            } catch (Exception ex) {
                if (errorHandler != null) {
                    errorHandler.handleError(ex);
                } else {
                    logger.error("Exception running embedded ZooKeeper", ex);
                }
            }
        }
    }
}