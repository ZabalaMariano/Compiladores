package TP_Final.observable;

public interface Observable 
{
	public void addObserver(Object o);
	public void notifyObserver(int op, String log);
}
