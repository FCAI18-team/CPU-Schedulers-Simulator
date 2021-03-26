public class myPair<T1, T2>
{
    public T1 first;
    public T2 second;
    public myPair() {}
    public myPair(T1 first, T2 second){
    this.first = first;
    this.second = second;
    }
    public void setValue(T1 a, T2 b)
    {
        this.first = a;
        this.second = b;
    }
    public myPair<T1, T2> getValue()
    {
            return this;
    }
    public T2 getSecond()
    {
        return second;
    }
}

