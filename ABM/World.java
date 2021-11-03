package ABM;

import java.awt.Color;
import java.util.ArrayList;

public class World {
	private int width, height;
	public Color canvasColor;
	ArrayList<Predator> predList = new ArrayList<Predator>();
	ArrayList<Prey> preyList = new ArrayList<Prey>();
	ArrayList<Animal> animList = new ArrayList<Animal>();
	Animal[][] worldMap;
	DotPanel dp;

	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		canvasColor = Helper.newRandColor();
		worldMap = new Animal[h][w];
		populate(numPrey, numPredator);
	}

	private void populate(int numPrey, int numPredators) {
		int i = 0;
		while (i < numPredators) {
			int x = Helper.nextInt(width);
			int y = Helper.nextInt(height);
			Predator pred = new Predator(x, y);
			worldMap[x][y] = pred;
			predList.add(pred);
			i++;
		}
	}

	public void update() {
		// move predators, prey etc
		for (int i = 0; i < predList.size(); i++) {
			predList.get(i).move();
			predList.get(i).reproduce();
			predList.get(i).die();
		}

		for (int i = 0; i < predList.size(); i++) {
			predsEat(predList.get(i).yPoint, predList.get(i).xPoint);
		}

		for (int i = 0; i < preyList.size(); i++) {
			preyList.get(i).move();
			preyList.get(i).reproduce();
			preyList.get(i).die();
		}

	}

	private void predsEat(int yPoint, int xPoint) {
		// TODO Auto-generated method stub

	}

	abstract class Animal {
		int xPoint;
		int yPoint;
		int moveDirection;
		String type;
		Color color;
		boolean chasing;

		public Animal(int xpoint, int ypoint) {
			this.xPoint = xpoint;
			this.yPoint = ypoint;

		}

		public abstract void move();

		public abstract void changeDirection();

		public abstract void reproduce();

		public abstract void die();

		public abstract void drawAnimal(DotPanel dotPanel);

	}

	class Predator extends Animal {

		public Predator(int xpoint, int ypoint) {
			super(xpoint, ypoint);
			this.moveDirection = Helper.nextInt(4);
			this.type = "pred";
			this.chasing = false;
			this.color = Color.RED;
		}

		public void die() {
			if (Helper.nextDouble() < 0.011) {
				worldMap[this.yPoint][this.xPoint] = null;
				predList.remove(this);
			}
		}

		public void reproduce() {
			if (Helper.nextDouble() < 0.0135) {
				int i = 0;
				int max = Helper.nextInt(4);
				while (i < max) {
					int x = Helper.nextInt(width);
					int y = Helper.nextInt(height);
					Predator pred = new Predator(x, y);
					worldMap[y][x] = pred;
					predList.add(pred);
					i++;
				}
			}

		}

		public void move() {
			// ensure is within bounds
			if (this.xPoint == width - 1) {
				this.moveDirection = 2;
			}

			if (this.yPoint == height - 1) {
				this.moveDirection = 0;
			}

			if (this.xPoint == 0) {
				this.moveDirection = 3;
			}

			if (this.yPoint == 0) {
				this.moveDirection = 1;
			}

			worldMap[this.yPoint][this.xPoint] = null;

			switch (this.moveDirection) {

			case 0:
				this.yPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;

			case 1:
				this.yPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;

			case 2:
				this.xPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;

			case 3:
				this.xPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			}

		}

		@Override
		public void changeDirection() {
			// if predator is not chasing prey
			if (!this.chasing) {
				if (Helper.nextDouble() < 0.05) {
					this.moveDirection = Helper.nextInt(4);
				}
			}
		}

		@Override
		public void drawAnimal(DotPanel dotPanel) {
			dotPanel.drawSquare(this.xPoint, this.yPoint, this.color);
		}
	}

	class Prey extends Animal {

		public Prey(int xpoint, int ypoint, Color c) {
			super(xpoint, ypoint);
			this.moveDirection = Helper.nextInt(4);
			this.type = "prey";
			this.chasing = false;
			this.color = c;
		}

		@Override
		public void move() {
			// ensure is within bounds
			if (this.xPoint == width - 1) {
				this.moveDirection = 2;
			}

			if (this.yPoint == height - 1) {
				this.moveDirection = 0;
			}

			if (this.xPoint == 0) {
				this.moveDirection = 3;
			}

			if (this.yPoint == 0) {
				this.moveDirection = 1;
			}

			worldMap[this.yPoint][this.xPoint] = null;

			switch (this.moveDirection) {

			case 0:
				this.yPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;

			case 1:
				this.yPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;

			case 2:
				this.xPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;

			case 3:
				this.xPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			}
		}

		@Override
		public void changeDirection() {
			if (!this.chasing) {
				if (Helper.nextDouble() < 0.1) {
					this.moveDirection = Helper.nextInt(4);
				}
			}
		}

		@Override
		public void reproduce() {
			if (Helper.nextDouble() < 0.03) {

				int x = Helper.nextInt(width);
				int y = Helper.nextInt(height);
				Color offSpringColor;
				if (Helper.nextDouble() < 0.1) {
					offSpringColor = Helper.newRandColor();
				} else {
					offSpringColor = this.color;
				}

				Prey offSpring = new Prey(x, y, offSpringColor);
				worldMap[y][x] = offSpring;
				preyList.add(offSpring);
			}
		}

		@Override
		public void die() {
			if (Helper.nextDouble() < 0.0135) {
				worldMap[this.yPoint][this.xPoint] = null;
				preyList.remove(this);
			}
		}

		@Override
		public void drawAnimal(DotPanel dotPanel) {
			dotPanel.drawCircle(this.xPoint, this.yPoint, this.color);

		}
		
		public void getEaten() {
			worldMap[this.yPoint][this.xPoint] = null;
			preyList.remove(this);
		}
	}

	public void draw(DotPanel canvas) {
		PPSim.dp.clear(canvasColor);
		for(int i=0;i<predList.size();i++) {
			predList.get(i).drawAnimal(canvas);
		}
		for(int i=0;i<preyList.size();i++) {
			preyList.get(i).drawAnimal(canvas);
		}
		
	}
}
