import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{

    String state;

    public void setState(String state){

        this.state = state;

    }

    public String getState(){

        return this.state;

    }

}
