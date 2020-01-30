import java.awt.*;

/**
   A shape that can be moved around.
*/
public interface MoveableShape
{
   void draw (Graphics2D g2);

   void move ();					//   void translate (int dr, int dc);

   boolean contains (int r, int c);
}
