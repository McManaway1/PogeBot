import com.pogebot.packs.pack.AbstractPack;
import com.pogebot.parsers.containers.CommandData;
import net.dv8tion.jda.core.events.Event;

public class ExamplePack extends AbstractPack {

    @Override
    public void initialize() {
        System.out.println("Example Pack has Started!");
    }

    @Override
    public void invoke(CommandData data) {
        if(data.getArguments().equals("test")) System.out.println("Received a Test Message!");
    }

    @Override
    public void onEvent(Event event) {
        System.out.println(event.getClass().getName() +" Received!");
    }

    @Override
    public void close() {
        System.out.println("Example Pack shutting Down!");
    }
}
