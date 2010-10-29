package game.elements; 

public class Coord {

	public float x = 0;
	public float y = 0;
	
	public Coord() {
		super();
	}
	
	public Coord(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Coord(Coord coord) {
		if (coord != null) {
			this.x = coord.x;
			this.y = coord.y;
		}
	}
	
	public void add(Coord coord) {
		if (coord != null) {
			this.x += coord.x;
			this.y += coord.y;
		}
	}
	
	public void sub(Coord coord) {
		if (coord != null) {
			this.x -= coord.x;
			this.y -= coord.y;
		}
	}
	
	public int distance(Coord coord){
		return (int)(Math.sqrt(Math.pow(coord.x - x, 2) + Math.pow(coord.y - y, 2)));
	}
	
	public String toString(){
		return x + "x" + y;
	}
	
	public Coord clone(){
		return new Coord(x, y);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Coord other = (Coord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
