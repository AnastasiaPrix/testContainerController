import io.herrmann.generator.Generator;

public class generatorValues  {
    private boolean f = true;
    public static void startGenerator () {
        Generator<Integer> simpleGenerator = new Generator<Integer>() {
            @Override
            protected void run() throws InterruptedException {
                while (true) {
                    yield(2);

                }
            }
        };

    }
}
