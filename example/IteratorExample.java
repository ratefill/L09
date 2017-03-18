package by.epam.l09.example;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class IteratorExample
{
	public static void main(String[] args)
	{
		Set<String> set = new HashSet<String>();
		set.add("London");
		set.add("Paris");
		set.add("New York");
		set.add("San Francisco");
		set.add("Berling");
		set.add("New York");
		System.out.println(set);
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext())
		{
			System.out.print(iterator.next() + " ");
		}
	}
}