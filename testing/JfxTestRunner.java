import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;

public class JfxTestRunner implements BeforeAllCallback {

    private static final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // Initialize JavaFX on a non-JavaFX thread using JFXPanel
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown(); // signal that JavaFX initialization is complete
        });

        // Wait for JavaFX initialization to complete
        if (!latch.await(30, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX initialization timed out");
        }
    }
}
