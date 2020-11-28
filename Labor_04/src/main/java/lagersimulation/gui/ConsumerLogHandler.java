package lagersimulation.gui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ConsumerLogHandler extends Handler {

    private final Consumer<String> consumer;

    public ConsumerLogHandler(Consumer<String> logConsumer) {
        this.consumer = Objects.requireNonNull(logConsumer);
    }

    @Override
    public void publish(LogRecord record) {
        consumer.accept(record.getMessage());
    }

    @Override
    public void flush() {
        //Nicht notwendig
    }

    @Override
    public void close() throws SecurityException {
        //Nicht notwendig
    }
}
