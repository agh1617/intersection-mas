import pl.edu.agh.student.intersection_mas.intersection.Intersection;
import pl.edu.agh.student.intersection_mas.intersection.Node;
import pl.edu.agh.student.intersection_mas.utils.IntersectionLoader;

import java.util.Set;

public class Main {
    public static void main(String[] args)  {
        akka.Main.main(new String[] { HelloWorld.class.getName() });

        Set<Node> inputNodes = IntersectionLoader.loadIntersection();
        Intersection intersection = new Intersection(inputNodes);

        System.out.println(intersection.toString());
    }
}