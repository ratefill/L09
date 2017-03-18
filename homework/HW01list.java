package by.epam.l09.homework;

import java.util.NoSuchElementException;

/*Реализуйте самостоятельно динамическую структуру Односвязный список.
Реализуйте методы добавления (в голову, хвост и произвольное место списка),
удаления и поиска объекта в списке. Используйте параметризацию при описании
класса. (Условие не означает необходимости повторить все возможности
класса LinkedList. Возможности Java Collection Framework не использовать).*/

public class HW01list
{
	public static void main(String[] args)
	{

		LinkList<String> list = new LinkList<String>();
		list.add("Машенька");
		list.add("Оленька");
		list.add("Дашенька");
		list.add(1, "Сонечка");
		list.print();
		System.out.println("Первый элемент: " + list.getFirst());
		System.out.println("Последний элемент: " + list.getLast());
		System.out.println("Есть ли в списке Машенька: " + list.exist("Машенька"));
		System.out.println("Вторая запись в списке: " + list.get(1));

		list.addFirst("Ксюшенька");
		System.out.println("Новый первый элемент: " + list.getFirst());

		list.addLast("Сашенька");
		System.out.println("Новый последний элемент: " + list.getLast());

		list.print();

		list.removeFirst();
		System.out.println("Новый первый элемент: " + list.getFirst());

		list.removeLast();
		System.out.println("Новый последний элемент: " + list.getLast());

		list.clear();
		list.add("Катенька");
		list.add("Женечька");
		list.add("Леночка");

		list.print();

		list.remove("Женечька");

		list.print();
		
		list.add("Юленька");
		
		list.print();
		list.remove(1);		
		list.print();
		list.remove(0);	
		list.print();
	}
}

class LinkList<T>
{
	private int size;
	private Node<T> first;
	private Node<T> last;

	public T getFirst()
	{
		final Node<T> f = first;
		if (f == null)
			throw new NoSuchElementException();
		return f.getvalue();
	}

	public T getLast()
	{
		final Node<T> node = last;
		if (node == null)
			throw new NoSuchElementException();
		return node.getvalue();
	}

	// добавить в начало списка
	public void addFirst(T value)
	{
		final Node<T> f = first;
		final Node<T> newNode = new Node<>(value, f);
		first = newNode;
		if (f == null) // список пустой
		{
			last = newNode;
		}
		size++;
	}

	// добавить в конец списка
	public void addLast(T value)
	{
		final Node<T> l = last;
		final Node<T> newNode = new Node<>(value, null);
		last = newNode;
		if (l == null)// список пустой
		{
			first = newNode;
		}
		else
		{
			l.setNext(newNode);
		}
		size++;
	}

	// добавляет после указанного элемента
	private void add(Node<T> node, T value)
	{
		if (node == null)
			throw new NoSuchElementException();
		final Node<T> next = node.getNext();
		final Node<T> newNode = new Node<>(value, next);
		node.setNext(newNode);

		if (next == null)
		{
			last = newNode;
		}
		size++;
	}

	// добавляет после указанного номера
	public void add(int index, T value)
	{
		if (!isElementIndex(index))
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

		Node<T> node = getNode(index);
		add(node, value);
	}

	// добавляет в конец списка
	public void add(T value)
	{
		addLast(value);
	}

	// удаляет первый элемент
	public void removeFirst()
	{
		final Node<T> f = first;
		if (f == null)
			throw new NoSuchElementException();

		final Node<T> next = f.getNext();

		f.setNext(null);
		f.setvalue(null);
		first = next;

		if (next == null)
			last = null;
		size--;
	}

	// удаляет последний элемент
	public void removeLast()
	{
		final Node<T> l = last;
		if (l == null)
			throw new NoSuchElementException();

		final Node<T> prev = prev(l);

		l.setNext(null);
		l.setvalue(null);

		last = prev;

		if (prev == null)
		{
			first = null;
		}
		size--;
	}

	// удаляет первый найденный по значению элемент
	public boolean remove(T value)
	{
		boolean result = false;

		Node<T> node, nodeprev;
		for (node = first, nodeprev = null; node != null; nodeprev = node, node = node.getNext())
		{
			T v = node.getvalue();

			if (v.hashCode() == value.hashCode() && v.equals(value))
			{
				Node<T> nodenext = node.getNext();

				if (nodeprev == null)
				{
					first = nodenext;
				}
				else
				{
					nodeprev.setNext(nodenext);
				}

				if (nodenext == null)
				{
					last = nodeprev;
				}

				size--;
				result = true;
				break;
			}
		}

		return result;
	}

	// удаляет элемент по номеру
	public boolean remove(int index)
	{
		boolean result = false;

		if (index == 0)
		{
			removeFirst();
			result = true;
		}
		else
		{
			Node<T> node = first;
			Node<T> nodeprev = null;

			for (int i = 1; i <= index; i++)
			{
				nodeprev = node;
				node = node.getNext();
			}
			Node<T> nodenext = node.getNext();

			if (nodeprev == null)
			{
				first = nodenext;
			}
			else
			{
				nodeprev.setNext(nodenext);
			}

			if (nodenext == null)
			{
				last = nodeprev;
			}

			size--;
			result = true;

		}

		return result;
	}

	// возвращает первый найденный элемент по значению
	private Node<T> contains(T value)
	{
		Node<T> result = null;
		for (Node<T> node = first; node != null; node = node.getNext())
		{
			T v = node.getvalue();
			if (v.hashCode() == value.hashCode() && v.equals(value))
			{
				result = node;
				break;
			}
		}
		return result;
	}

	// проверка существования
	public boolean exist(T value)
	{
		return contains(value) != null;
	}

	// возвращает предыдущий элемент относительно текущего
	private Node<T> prev(Node<T> node)
	{
		Node<T> result = null;
		for (Node<T> n = first; n != null; n = n.getNext())
		{
			if (n.getNext() == node)
			{
				result = n;
				break;
			}
		}
		return result;
	}

	// удалить все
	public void clear()
	{

		for (Node<T> node = first; node != null;)
		{
			Node<T> next = node.getNext();
			node.setNext(null);
			node.setvalue(null);
			node = next;
		}
		first = last = null;
		size = 0;
	}

	// проверка корректности номера элемента
	private boolean isElementIndex(int index)
	{
		return index >= 0 && index < size;
	}

	// Получаем элемент по номеру
	private Node<T> getNode(int index)
	{
		if (!isElementIndex(index))
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

		Node<T> x = first;
		for (int i = 0; i < index; i++)
			x = x.getNext();
		return x;
	}

	// Получаем значение по номеру
	public T get(int index)
	{
		return getNode(index).getvalue();
	}

	// конвертация в массив
	public T[] toArray()
	{
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[size];
		int i = 0;
		for (Node<T> node = first; node != null; node = node.getNext())
			result[i++] = node.getvalue();
		return result;
	}

	// вывод на экран всего списка
	public void print()
	{
		T[] arr = toArray();
		System.out.print("Список: ");
		for (T item : arr)
		{
			System.out.print(item.toString() + "; ");
		}
		System.out.println();
	}
}

class Node<T>
{
	private T value;
	private Node<T> next;

	public Node(T value, Node<T> next)
	{
		this.value = value;
		this.next = next;
	}

	public T getvalue()
	{
		return value;
	}

	public void setvalue(T value)
	{
		this.value = value;
	}

	public Node<T> getNext()
	{
		return next;
	}

	public void setNext(Node<T> next)
	{
		this.next = next;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		// result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Node<?> other = (Node<?>) obj;

		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;

		return true;
	}

	@Override
	public String toString()
	{
		String result;
		result = (value == null) ? "" : value.toString();

		return result;

	}

}
