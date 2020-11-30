package observer;

import java.time.Instant;

public class TreeSetEvent<T> {
    private final ObservableTreeSet<T> source;
    private final Instant timestamp;
    private final String info;

    public TreeSetEvent(ObservableTreeSet<T> source, String info) {
        this.source = source;
        this.timestamp = Instant.now();
        this.info = info;
    }
}
