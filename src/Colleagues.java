import java.util.Objects;

/**
 * A class representing a pairing of Actors, which includes
 * their names and the number of movies they have acted in
 * together.
 * 
 * TODO:  You must correctly implement the 4 empty methods below.
 */
public class Colleagues implements Comparable<Colleagues>
{
    public String actor1;
    public String actor2;
    public int numMoviesShared;
    
    public Colleagues(String a1, String a2, int n)
    {
        actor1 = a1;
        actor2 = a2;
        numMoviesShared = n;
    }
    
    public int compareTo(Colleagues o)
    {
        return -((Integer)numMoviesShared).compareTo(o.numMoviesShared);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Colleagues)) return false;
        Colleagues that = (Colleagues) o;
        return ((actor1.equals(that.actor2)) && (actor2.equals(that.actor1))
                        ||
                        (actor1.equals(that.actor1)) && (actor2.equals(that.actor2))
                );
    }

    @Override
    public int hashCode() {
        return actor1.hashCode() + actor2.hashCode();
    }

    @Override
    public String toString() {
        return "Colleagues{" +
                "actor1='" + actor1 + '\'' +
                ", actor2='" + actor2 + '\'' +
                ", numMoviesShared=" + numMoviesShared +
                '}';
    }
}

