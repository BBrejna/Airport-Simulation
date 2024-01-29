import controller.ControllersHandler;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ExtendWith(JfxTestRunner.class)
public class MainAppTests {
    private static ControllersHandler controllersHandler;
    private static CountDownLatch uiInitializedLatch;

    @BeforeAll
    public static void init() throws Exception {
        controllersHandler = ControllersHandler.getInstance();
        uiInitializedLatch = new CountDownLatch(1);

        new JFXPanel();

        Platform.runLater(() -> {
            try {
                new MainApp().start(new Stage());
                uiInitializedLatch.countDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        if (!uiInitializedLatch.await(30, TimeUnit.SECONDS)) {
            throw new IllegalStateException("UI initialization timed out");
        }
    }

    @Test
    public void checkControllersHandler() {
        Platform.runLater(() -> {
            assertNotNull(controllersHandler.getMainViewController());
            assertNotNull(controllersHandler.getSimulationViewController());
            assertNotNull(controllersHandler.getAdminViewController());
            assertNotNull(controllersHandler.getSalesmanViewController());
            assertNotNull(controllersHandler.getStatisticsViewController());
        });
    }

}
