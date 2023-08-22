package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author saugat
 */
@Named
@ViewScoped
public class CarouselController implements Serializable {

    public List<String> getHomePageCarouselList() {
        List<String> carouselList = new ArrayList<>();
        carouselList.add("1.png");
        carouselList.add("2.png");
        carouselList.add("3.png");
        carouselList.add("4.png");

        return carouselList;
    }
}
