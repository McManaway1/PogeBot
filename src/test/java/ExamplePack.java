import com.teambeez.parsers.containers.CommandData;
import com.teambeez.packs.pack.IPack;
import net.dv8tion.jda.core.events.Event;

public class ExamplePack implements IPack{

    @Override
    public void initialize() {
        System.out.println("Start!");
    }

    @Override
    public void invoke(CommandData data) {
        System.out.println("Invoked!");
    }

    @Override
    public void onEvent(Event event) {
        System.out.println("Event Occurred!");
    }

    @Override
    public void close() {
        System.out.println("Closed!");
    }
}
