class Point {
  // TODO
        private double x;
        private double y;

        public Point(double x, double y) {
                this.x = x;
                this.y = y;
}

        public String toString() {
                String output = "(" + x + ", " + y + ")";
                return output;
        }




        public double getX() {
                return x;
        }

        public double getY() {
                return y;
        }
}
