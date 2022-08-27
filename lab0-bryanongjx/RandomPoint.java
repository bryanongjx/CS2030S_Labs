import java.util.Random;

class RandomPoint extends Point {

	public static Random rng = new Random(1);

        public static void setSeed(int x){
                rng = new Random(x);
        }

        public RandomPoint(double minX, double maxX, double minY, double maxY){
                super(rng.nextDouble() * (maxX-minX) + minX, rng.nextDouble() * (maxY-minY) + minY);
        }
}
