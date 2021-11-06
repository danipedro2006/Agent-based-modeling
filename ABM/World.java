package ABM;

import java.awt.Color;
import java.util.ArrayList;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Color;

public class World {

	private int width, height;
	public Color canavasColor;

	ArrayList<predator> predList = new ArrayList<predator>();

	ArrayList<prey> preyList = new ArrayList<prey>();

	Animal[][] worldMap;
	DotPanel dp;

	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		canavasColor = Color.GREEN;
		worldMap = new Animal[h][w];
		populate(numPrey, numPredator);
	}

	private void populate(int numPrey, int numPredators) {
		int i = 0;
		while (i < numPredators) {
			int x = Helper.nextInt(width);
			int y = Helper.nextInt(height);
			predator pred = new predator(x, y); // x,y location ON SCREEN
			worldMap[y][x] = pred; // y and x are transposed
			predList.add(pred); // add each newly initialized predator to predList
			i++;
		}

		int j = 0;
		while (j < numPrey) {
			int x = Helper.nextInt(width);
			int y = Helper.nextInt(height);
			prey prey = new prey(x, y, Color.blue); // each prey has a blue color
			worldMap[y][x] = prey; // y and x are transposed
			preyList.add(prey); // add each newly initialized prey to preyList
			j++;
		}

	}

	public void update() {

		for (int i = 0; i < predList.size(); i++) {
			predList.get(i).move();
			predList.get(i).reproduce();
			predList.get(i).die();
		}

		for (int i = 0; i < predList.size(); i++) {
			predsEat(predList.get(i).yPoint, predList.get(i).xPoint); // (y,x) is its location in the 2D array
		}

		for (int i = 0; i < preyList.size(); i++) {
			preyList.get(i).move();
			preyList.get(i).reproduce();
			preyList.get(i).die();
		}

		for (int i = 0; i < predList.size(); i++) {
			predsEat(predList.get(i).yPoint, predList.get(i).xPoint);
		}

		for (int i = 0; i < predList.size(); i++) {
			predScanAround(predList.get(i).yPoint, predList.get(i).xPoint);
		}

		for (int i = 0; i < preyList.size(); i++) {
			preyScanAround(preyList.get(i).yPoint, preyList.get(i).xPoint);
		}

		for (int i = 0; i < predList.size(); i++) {
			predList.get(i).changeDirection();
		}
		for (int i = 0; i < preyList.size(); i++) {
			preyList.get(i).changeDirection();
		}
	}

	public void draw(DotPanel canvas) { // takes the canvas the simulation is drawing on

		PPSim.dp.clear(canavasColor);

		for (int i = 0; i < predList.size(); i++) {
			predList.get(i).drawAnimal(canvas);
		}

		for (int i = 0; i < preyList.size(); i++) {
			preyList.get(i).drawAnimal(canvas);
		}
	}

	public boolean SquaresRight(int x, int y, int numSquares, String spectator, String observe) {

		if (worldMap[x][y] != null && worldMap[x][y].type == spectator && (width - 1 - y >= numSquares)) {
			for (int k = 1; k <= numSquares; k++) {
				// [y + k] is number of columns after [x][y]
				if (worldMap[x][y + k] != null && worldMap[x][y + k].type == observe) {
					// if any of the squares follows the condition, return true
					return true;
				}
			}

			return false;
		}

		else if (worldMap[x][y] != null && worldMap[x][y].type == spectator) {
			int remDist = width - 1 - y; // remaining squares until right-border is reached
			for (int k = 1; k <= remDist; k++) {
				if (worldMap[x][y + k] != null && worldMap[x][y + k].type == observe) {
					return true;
				}
			}

			return false;
		}
		return false;
	}

	public boolean SquaresLeft(int x, int y, int numSquares, String spectator, String observe) {
		if (worldMap[x][y] != null && worldMap[x][y].type == spectator && (y >= numSquares)) {
			for (int k = 1; k <= numSquares; k++) {
				if (worldMap[x][y - k] != null && worldMap[x][y - k].type == observe) {
					return true;
				}
			}

			return false;
		}

		else if (worldMap[x][y] != null && worldMap[x][y].type == spectator) {
			int remDist = y; // remaining squares until left-border is reached
			for (int k = 1; k <= remDist; k++) {
				if (worldMap[x][y - k] != null && worldMap[x][y - k].type == observe) {
					return true;
				}
			}

			return false;
		}
		return false;
	}

	public boolean SquaresUp(int x, int y, int numSquares, String spectator, String observe) {
		if (worldMap[x][y] != null && worldMap[x][y].type == spectator && (x >= numSquares)) {
			for (int k = 1; k <= numSquares; k++) {
				if (worldMap[x - k][y] != null && worldMap[x - k][y].type == observe) {
					// if any of the squares follows the condition, return true
					return true;
				}
			}
			return false;
		}

		else if (worldMap[x][y] != null && worldMap[x][y].type == spectator) {
			int remDist = x; // remaining squares until top border is reached
			for (int k = 1; k <= remDist; k++) {
				if (worldMap[x - k][y] != null && worldMap[x - k][y].type == observe) {
					return true;
				}
			}

			return false;
		}
		return false;
	}

	public boolean SquaresDown(int x, int y, int numSquares, String spectator, String observe) {
		if (worldMap[x][y] != null && worldMap[x][y].type == spectator && (height - 1 - x >= numSquares)) {
			for (int k = 1; k <= numSquares; k++) {
				if (worldMap[x + k][y] != null && worldMap[x + k][y].type == observe) {
					return true;
				}
			}
			return false;
		} else if (worldMap[x][y] != null && worldMap[x][y].type == spectator) {
			int remDist = height - 1 - x; // remaining squares until bottom border is reached
			for (int k = 1; k <= remDist; k++) {
				if (worldMap[x + k][y] != null && worldMap[x + k][y].type == observe) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public void predScanAround(int i, int j) {

		if (worldMap[i][j] != null && worldMap[i][j].type == "pred") {
			worldMap[i][j].chasing = false;

			if (SquaresRight(i, j, 15, "pred", "prey")) {
				worldMap[i][j].moveDirection = 3;
				worldMap[i][j].chasing = true;
			}

			if (SquaresLeft(i, j, 15, "pred", "prey")) {
				worldMap[i][j].moveDirection = 2;
				worldMap[i][j].chasing = true;
			}

			if (SquaresUp(i, j, 15, "pred", "prey")) {
				worldMap[i][j].moveDirection = 0;
				worldMap[i][j].chasing = true;
			}

			if (SquaresDown(i, j, 15, "pred", "prey")) {
				worldMap[i][j].moveDirection = 1;
				worldMap[i][j].chasing = true;
			}
		}
	}

	public void preyScanAround(int i, int j) {

		if (worldMap[i][j] != null && worldMap[i][j].type == "prey") {

			worldMap[i][j].chasing = false;
			if (SquaresRight(i, j, 10, "prey", "pred")) {
				worldMap[i][j].moveDirection = 2; // move left
				if (j > 0 && worldMap[i][j].type == "prey") {
					worldMap[i][j].move();
				}
			}

			if (SquaresLeft(i, j, 10, "prey", "pred")) {
				worldMap[i][j].moveDirection = 3; // move right

				if (j < width - 1 && worldMap[i][j].type == "prey") {

					worldMap[i][j].move();
				}
			}

			if (SquaresUp(i, j, 10, "prey", "pred")) {
				worldMap[i][j].moveDirection = 1; // move down

				if (i < height - 1 && worldMap[i][j].type == "prey") {
					// move additonal step
					worldMap[i][j].move();
				}
			}

			if (SquaresDown(i, j, 10, "prey", "pred")) {
				worldMap[i][j].moveDirection = 0; // move up

				if (i > 0 && worldMap[i][j].type == "prey") {
					// move additional step
					worldMap[i][j].move();
				}
			}
		}
	}

	public void predsEat(int i, int j) {

		if (worldMap[i][j] != null && worldMap[i][j].type == "pred")
			if (i == 0) {

				if (j == 0) {
					// look down, right
					adjacent(i, j, "down");
					adjacent(i, j, "right");
				}

				else if (j == width - 1) {
					// look left, down
					adjacent(i, j, "left");
					adjacent(i, j, "down");
				}

				else {
					// look left down right
					adjacent(i, j, "left");
					adjacent(i, j, "down");
					adjacent(i, j, "right");
				}
			}

			else if (j == 0) {

				if (i == height - 1) {
					// look up, right
					adjacent(i, j, "right");
				}

				else {
					// look up, right, down
					adjacent(i, j, "up");
					adjacent(i, j, "right");
					adjacent(i, j, "down");
				}
			}

			else if (i == height - 1) {

				if (j == width - 1) {
					// look up, left
					adjacent(i, j, "left");
					adjacent(i, j, "up");
				}

				else {
					// look left, right, up
					adjacent(i, j, "left");
					adjacent(i, j, "right");
					adjacent(i, j, "up");
				}
			}

			else if (j == width - 1) {
				// look up, down, left
				adjacent(i, j, "up");
				adjacent(i, j, "down");
				adjacent(i, j, "left");
			}

			else {
				// look at all 4 directions
				adjacent(i, j, "up");
				adjacent(i, j, "down");
				adjacent(i, j, "left");
				adjacent(i, j, "right");
			}

	}

	public void adjacent(int x, int y, String direction) {
		if (direction == "right" && worldMap[x][y] != null) {
			if (worldMap[x][y].type == "pred" && worldMap[x][y + 1] != null) {
				if (worldMap[x][y + 1].type == "prey" && worldMap[x][y + 1].color != canavasColor) {
					((prey) worldMap[x][y + 1]).getEaten();
				}
			}
		}

		if (direction == "left" && worldMap[x][y] != null) {
			if (worldMap[x][y].type == "pred" && worldMap[x][y - 1] != null) {
				if (worldMap[x][y - 1].type == "prey" && worldMap[x][y - 1].color != canavasColor) {
					((prey) worldMap[x][y - 1]).getEaten();
				}
			}
		}

		if (direction == "down" && worldMap[x][y] != null) {
			if (worldMap[x][y].type == "pred" && worldMap[x + 1][y] != null) {
				if (worldMap[x + 1][y].type == "prey" && worldMap[x + 1][y].color != canavasColor) {
					((prey) worldMap[x + 1][y]).getEaten();
				}
			}
		}

		if (direction == "up" && worldMap[x][y] != null) {
			if (worldMap[x][y].type == "pred" && worldMap[x - 1][y] != null) {
				if (worldMap[x - 1][y].type == "prey" && direction == "up"
						&& worldMap[x - 1][y].color != canavasColor) {
					((prey) worldMap[x - 1][y]).getEaten();
				}
			}
		}
	}

	public void OnClickPrey(int x, int y) {
		prey onClickedPrey = new prey(x, y, Color.BLUE);
		worldMap[y][x] = onClickedPrey;
		preyList.add(onClickedPrey);
	}

	public void onPressedPred() {
		int x = Helper.nextInt(width);
		int y = Helper.nextInt(height);
		predator pred = new predator(x, y); // each prey has a random color
		worldMap[y][x] = pred;
		predList.add(pred);
	}

	abstract class Animal {
		int xPoint;
		int yPoint;
		// direction to move
		int moveDirection;
		// either "pred" or "prey"
		String type;
		Color color;
		boolean chasing;

		public Animal(int xpoint, int ypoint) {
			this.xPoint = xpoint;
			this.yPoint = ypoint;
		}

		// Methods in this class
		public abstract void move();

		public abstract void changeDirection();

		public abstract void reproduce();

		public abstract void die();

		public abstract void drawAnimal(DotPanel dotPanel);
	}

	class predator extends Animal {
		public predator(int xpoint, int ypoint) {
			super(xpoint, ypoint);
			this.moveDirection = Helper.nextInt(4);
			this.type = "pred";
			this.color = Color.RED;
			this.chasing = false;
		}

		@Override
		public void move() {

			if (this.xPoint == width - 1) { // if at rightmost
				this.moveDirection = 2; // make predator move left
			}
			if (this.yPoint == height - 1) { // if at very bottom
				this.moveDirection = 0; // make predator go up
			}
			if (this.xPoint == 0) { // if at very leftmost
				this.moveDirection = 3; // make predator go right
			}
			if (this.yPoint == 0) { // if at very top
				this.moveDirection = 1; // make predator go down
			}
			worldMap[this.yPoint][this.xPoint] = null;

			switch (this.moveDirection) {
			// Go up
			case 0:
				this.yPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			// Go down
			case 1:
				this.yPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			// Go left
			case 2:
				this.xPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			// Go right
			case 3:
				this.xPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			}
		}

		@Override
		public void changeDirection() {
			// if predator is not chasing a prey
			if (!this.chasing) {
				if (Helper.nextDouble() < 0.05) {
					// randomly select a new direction
					this.moveDirection = Helper.nextInt(4);
				}
			}
		}

		@Override
		public void reproduce() {
			if (Helper.nextDouble() < 0.0135) {
				int i = 0;
				int max = Helper.nextInt(4);
				while (i < max) {
					int x = Helper.nextInt(width);
					int y = Helper.nextInt(height);
					predator pred = new predator(x, y);
					worldMap[y][x] = pred;
					predList.add(pred);
					i++;
				}
			}
		}

		@Override
		public void die() {
			if (Helper.nextDouble() < 0.011) {
				worldMap[this.yPoint][this.xPoint] = null; // set its current location null
				predList.remove(this); // remove the object from predList if it dies
			}
		}

		@Override
		public void drawAnimal(DotPanel dotPanel) {
			// predators are represented as red squares
			dotPanel.drawSquare(this.xPoint, this.yPoint, this.color);
		}
	}

	class prey extends Animal {

		public prey(int xpoint, int ypoint, Color c) {
			super(xpoint, ypoint);
			this.moveDirection = Helper.nextInt(4);
			this.type = "prey";
			this.color = c;
			this.chasing = false;
		}

		@Override
		public void move() {

			if (this.xPoint == width - 1) { // if at rightmost
				this.moveDirection = 2; // make predator move left
			}
			if (this.yPoint == height - 1) { // if at very bottom
				this.moveDirection = 0; // make predator go up
			}
			if (this.xPoint == 0) { // if at very leftmost
				this.moveDirection = 3; // make predator go right
			}
			if (this.yPoint == 0) { // if at very top
				this.moveDirection = 1; // make predator go down
			}

			worldMap[this.yPoint][this.xPoint] = null;

			switch (this.moveDirection) {
			// Go up
			case 0:
				this.yPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			// Go down
			case 1:
				this.yPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			// Go left
			case 2:
				this.xPoint--;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			// Go right
			case 3:
				this.xPoint++;
				worldMap[this.yPoint][this.xPoint] = this;
				break;
			}
		}

		@Override
		public void changeDirection() {
			// if a predator is not located nearby
			if (!this.chasing) {
				if (Helper.nextDouble() < 0.1) {
					// randomly select a new direction
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
				// chance for the offspring to have a new color
				if (Helper.nextDouble() < 0.1) {
					offSpringColor = Color.YELLOW;
				} else {
					// otherwhise, it will have the same color as the parent
					offSpringColor = this.color;
				}
				prey offspring = new prey(x, y, offSpringColor);
				worldMap[y][x] = offspring;
				preyList.add(offspring);
			}
		}

		@Override
		public void die() {
			if (Helper.nextDouble() < 0.0135) {
				worldMap[this.yPoint][this.xPoint] = null;
				preyList.remove(this); // remove the object from preyList if it dies
			}
		}

		@Override
		public void drawAnimal(DotPanel dotPanel) {
			dotPanel.drawCircle(this.xPoint, this.yPoint, this.color);
		}

		// called when a predator object is located adjacent to
		// a prey object
		public void getEaten() {
			worldMap[this.yPoint][this.xPoint] = null; // make current position in array null
			preyList.remove(this);
		}
	}
}
