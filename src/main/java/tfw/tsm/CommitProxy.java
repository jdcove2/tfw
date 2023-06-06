package tfw.tsm;

import tfw.check.Argument;

public final class CommitProxy implements Proxy {
    private final Commit commit;

    public CommitProxy(Commit commit) {
        Argument.assertNotNull(commit, "commit");

        this.commit = commit;
    }

    public String getName() {
        return (commit.getName());
    }

    public SinkProxy[] getSinkProxies() {
        Object[] sinks = (Object[]) commit.sinks.values().toArray();
        SinkProxy[] sp = new SinkProxy[sinks.length];

        for (int i = 0; i < sinks.length; i++) {
            sp[i] = new SinkProxy((Sink) sinks[i]);
        }
        return (sp);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CommitProxy) {
            CommitProxy ip = (CommitProxy) obj;

            return (commit.equals(ip.commit));
        }

        return (false);
    }

    public int hashCode() {
        return (commit.hashCode());
    }
}
