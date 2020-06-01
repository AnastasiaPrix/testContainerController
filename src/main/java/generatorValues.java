import io.herrmann.generator.Generator;
import jade.core.behaviours.Behaviour;

public class generatorValues extends Behaviour {
    private boolean f = true;

        Generator<Integer> simpleGenerator = new Generator<Integer>() {
            @Override
            protected void run() throws InterruptedException {
                while (f) {

                    yield(2);
                }
            }
        };

    @Override
    public void action() {

    }

    @Override
    public boolean done() {
        return false;
    }
}
