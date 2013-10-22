package anfis;

public class ANFIS {
	private Layer[] layer;
	
	public ANFIS() {
		layer = new Layer[4];
		for(int i = 0; i < 4; i++) {
			layer[i] = new Layer();
		}
	}
	
	public Layer getLayer(int id) {
		return layer[id-1];
	}
}
