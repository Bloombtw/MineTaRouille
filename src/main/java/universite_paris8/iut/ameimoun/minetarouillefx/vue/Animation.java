package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation {

    private final ImageView imageView;
    private final Image[] frames;
    private final Timeline timeline;
    private int frameIndex = 0;

    public Animation(ImageView imageView, Image[] frames, int durationPerFrameMillis) {
        this.imageView = imageView;
        this.frames = frames;

        timeline = new Timeline(new KeyFrame(Duration.millis(durationPerFrameMillis), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                frameIndex = (frameIndex + 1) % frames.length;
                imageView.setImage(frames[frameIndex]);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        frameIndex = 0;
        imageView.setImage(frames[0]);
        timeline.playFromStart();
    }

    public void stop() {
        timeline.stop();
    }

    public boolean isRunning() {
        return timeline.getStatus() == Timeline.Status.RUNNING;
    }
}
