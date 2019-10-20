import java.util.List;

public class TraceNode {
    int val;
    List<Integer> track;

    public TraceNode() {
    }

    public TraceNode(int val, List<Integer> track) {
        this.val = val;
        this.track = track;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public List<Integer> getTrack() {
        return track;
    }

    public void setTrack(List<Integer> track) {
        this.track = track;
    }
}
