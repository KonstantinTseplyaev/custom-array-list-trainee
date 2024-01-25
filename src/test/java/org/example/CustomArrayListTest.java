package org.example;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomArrayListTest {

    @Nested
    @DisplayName("add(T t)")
    public class MethodAdd {
        @Test
        public void add_whenSizeLessDefaultCapacity() {
            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("first str");
            stringList.add("second str");
            stringList.add("third str");
            stringList.add("fourth str");

            assertEquals(4, stringList.size());
            assertTrue(stringList.hasElement("fourth str"));
            assertTrue(stringList.hasElement("first str"));
        }

        @Test
        public void add_whenSizeMoreDefaultCapacity() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            assertEquals(20, integerList.size());
            assertTrue(integerList.hasElement(12));
            assertTrue(integerList.hasElement(19));
            assertTrue(integerList.hasElement(1));
        }

        @Test
        public void add_whenItIsDuplicate() {
            CustomList<String> stringList = new CustomArrayList<>();
            String duplicate = "third str";
            stringList.add("first str");
            stringList.add("second str");
            stringList.add(duplicate);
            stringList.add(duplicate);
            stringList.add("fourth str");

            assertEquals(5, stringList.size());
            assertEquals(duplicate, stringList.get(2));
            assertEquals(duplicate, stringList.get(3));
        }

        @Test
        public void add_withCustomCapacity() {
            CustomList<Integer> integerList = new CustomArrayList<>(2);

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            assertEquals(20, integerList.size());
            assertTrue(integerList.hasElement(12));
            assertTrue(integerList.hasElement(19));
            assertTrue(integerList.hasElement(1));
        }
    }

    @Nested
    @DisplayName("add(T t, int index)")
    public class MethodAddByIndex {
        @Test
        public void addByIndex_whenIndexIsNegative() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> integerList.add(99999, -1));
            assertEquals("Index cannot be negative", exception.getMessage());
        }

        @Test
        public void addByIndex_whenIndexIsMoreSize() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                    () -> integerList.add(99999, 20));
            assertEquals("Index: " + 20 + ", Size: " + 20, exception.getMessage());
        }

        @Test
        public void addByIndex_whenIndexIsCorrect() {
            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("first str");
            stringList.add("second str");
            stringList.add("third str");
            stringList.add("fourth str");
            stringList.add("new value", 1);

            assertTrue(stringList.hasElement("new value"));
            assertEquals(5, stringList.size());
            assertEquals("first str", stringList.get(0));
            assertEquals("new value", stringList.get(1));
            assertEquals("second str", stringList.get(2));
            assertEquals("third str", stringList.get(3));
            assertEquals("fourth str", stringList.get(4));
        }
    }

    @Nested
    @DisplayName("get(int index)")
    public class MethodGetByIndex {
        @Test
        public void getByIndex_whenIndexIsNegative() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> integerList.get(-1));
            assertEquals("Index cannot be negative", exception.getMessage());
        }

        @Test
        public void getByIndex_whenIndexIsMoreSize() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                    () -> integerList.get(20));
            assertEquals("Index: " + 20 + ", Size: " + 20, exception.getMessage());
        }

        @Test
        public void getByIndex_whenIndexIsCorrect() {
            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("first str");
            stringList.add("second str");
            stringList.add("third str");
            stringList.add("fourth str");

            assertEquals("first str", stringList.get(0));
        }
    }

    @Nested
    @DisplayName("remove(T element)")
    public class MethodRemoveByValue {

        @Test
        public void removeByValue_whenListHasValue() {
            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("first str");
            stringList.add("second str");
            stringList.add("third str");
            stringList.add("fourth str");

            assertEquals("fourth str", stringList.get(3));
            assertEquals("second str", stringList.get(1));
            stringList.remove("second str");
            assertFalse(stringList.hasElement("second str"));
            IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                    () -> stringList.get(3));
            assertEquals("Index: " + 3 + ", Size: " + 3, exception.getMessage());
            assertEquals("third str", stringList.get(1));
        }

        @Test
        public void removeByValue_whenListDoesNotHaveValue() {
            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("first str");
            stringList.add("second str");
            stringList.add("third str");
            stringList.add("fourth str");

            stringList.remove("i'm not in the list!");
            assertEquals(4, stringList.size());
        }

        @Test
        public void removeByValue_withDuplicate() {
            CustomList<String> stringList = new CustomArrayList<>();
            String duplicate = "first str";
            stringList.add(duplicate);
            stringList.add("second str");
            stringList.add(duplicate);
            stringList.add("fourth str");

            assertEquals(duplicate, stringList.get(0));
            assertEquals(duplicate, stringList.get(2));
            stringList.remove(duplicate);
            assertTrue(stringList.hasElement(duplicate));
            IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                    () -> stringList.get(3));
            assertEquals("Index: " + 3 + ", Size: " + 3, exception.getMessage());
            assertEquals("fourth str", stringList.get(2));
        }
    }

    @Nested
    @DisplayName("remove(int index)")
    public class MethodRemoveByIndex {
        @Test
        public void removeByIndex_whenIndexIsNegative() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> integerList.remove(-1));
            assertEquals("Index cannot be negative", exception.getMessage());
        }

        @Test
        public void removeByIndex_whenIndexIsMoreSize() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,
                    () -> integerList.remove(20));
            assertEquals("Index: " + 20 + ", Size: " + 20, exception.getMessage());
        }

        @Test
        public void removeByIndex_whenIndexIsCorrect() {
            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("first str");
            stringList.add("second str");
            stringList.add("third str");
            stringList.add("fourth str");

            assertEquals(4, stringList.size());
            assertEquals("third str", stringList.get(2));
            stringList.remove(2);
            assertFalse(stringList.hasElement("third str"));
            assertEquals("fourth str", stringList.get(2));
            assertEquals(3, stringList.size());
        }
    }

    @Nested
    @DisplayName("removeAll()")
    public class MethodRemoveAll {

        @Test
        public void removeAll_whenListIsNotEmpty() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 20; i++) {
                integerList.add(i);
            }

            assertEquals(20, integerList.size());
            assertTrue(integerList.hasElement(0));
            assertTrue(integerList.hasElement(19));
            integerList.removeAll();
            assertEquals(0, integerList.size());
            assertFalse(integerList.hasElement(0));
            assertFalse(integerList.hasElement(19));
        }

        @Test
        public void removeAll_whenListIsEmpty() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            integerList.removeAll();
            assertEquals(0, integerList.size());
        }
    }

    @Nested
    @DisplayName("trimCapacityToSize()")
    public class MethodTrimCapacityToSize {
        @Test
        public void trimCapacityToSize_whenSizeIsNull() throws NoSuchFieldException, IllegalAccessException {
            CustomList<Integer> integerList = new CustomArrayList<>(500);
            Field capacity = integerList.getClass().getDeclaredField("capacity");
            capacity.setAccessible(true);

            assertEquals(500, capacity.getInt(integerList));
            integerList.trimCapacityToSize();
            assertEquals(10, capacity.getInt(integerList));
        }

        @Test
        public void trimCapacityToSize_whenSizeIsNotNull() throws NoSuchFieldException, IllegalAccessException {
            CustomList<Integer> array = new CustomArrayList<>();
            for (int i = 0; i < 100; i++) {
                array.add(i);
            }

            Field cap1 = array.getClass().getDeclaredField("capacity");
            cap1.setAccessible(true);
            assertTrue(cap1.getInt(array) > 100);

            array.trimCapacityToSize();

            Field cap2 = array.getClass().getDeclaredField("capacity");
            cap2.setAccessible(true);
            assertEquals(100, cap2.getInt(array));

            array.add(999);

            Field cap3 = array.getClass().getDeclaredField("capacity");
            cap3.setAccessible(true);
            assertEquals(((100 * 3) / 2 + 1), cap3.getInt(array));
        }
    }

    @Nested
    @DisplayName("sort()")
    public class MethodSort {
        @Test
        public void sortList_withListHasString() {
            CustomList<String> nameList = new CustomArrayList<>();
            nameList.add("Nick");
            nameList.add("Alice");
            nameList.add("Claire");
            nameList.add("Thom");
            nameList.add("Peggy");
            nameList.add("Jonny");

            nameList.sort(String.CASE_INSENSITIVE_ORDER);
            assertEquals("Alice", nameList.get(0));
            assertEquals("Claire", nameList.get(1));
            assertEquals("Jonny", nameList.get(2));
            assertEquals("Nick", nameList.get(3));
            assertEquals("Peggy", nameList.get(4));
            assertEquals("Thom", nameList.get(5));
        }

        @Test
        public void sortList_withListHasDuplicate() {
            CustomList<String> nameList = new CustomArrayList<>();
            nameList.add("Nick");
            nameList.add("Alice");
            nameList.add("Claire");
            nameList.add("Thom");
            nameList.add("Peggy");
            nameList.add("Jonny");
            nameList.add("Claire");

            nameList.sort(String.CASE_INSENSITIVE_ORDER);
            assertEquals("Alice", nameList.get(0));
            assertEquals("Claire", nameList.get(1));
            assertEquals("Claire", nameList.get(2));
            assertEquals("Jonny", nameList.get(3));
            assertEquals("Nick", nameList.get(4));
            assertEquals("Peggy", nameList.get(5));
            assertEquals("Thom", nameList.get(6));
        }

        @Test
        public void sortList_withListHasCustomClass() {
            Person nick = new Person("Nick", 23);
            Person claire = new Person("Claire", 49);
            Person jonny = new Person("Jonny", 25);
            Person peggy = new Person("Peggy", 18);
            Person thom = new Person("Thom", 35);
            CustomList<Person> personList = new CustomArrayList<>();
            personList.add(nick);
            personList.add(claire);
            personList.add(jonny);
            personList.add(peggy);
            personList.add(thom);
            Comparator<Person> ageComp = Comparator.comparing(Person::getAge);
            personList.sort(ageComp);

            assertEquals("Peggy", personList.get(0).getName());
            assertEquals("Nick", personList.get(1).getName());
            assertEquals("Jonny", personList.get(2).getName());
            assertEquals("Thom", personList.get(3).getName());
            assertEquals("Claire", personList.get(4).getName());
        }
    }

    @Nested
    @DisplayName("hasElement()")
    public class MethodHasElement {
        @Test
        public void hasElement_whenListDoesNotHaveElement() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 1000; i++) {
                integerList.add(i);
            }

            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("Peggy");
            stringList.add("Thom");

            assertFalse(integerList.hasElement(5000));
            assertFalse(stringList.hasElement("Claire"));
        }

        @Test
        public void hasElement_whenListHasElement() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 1000; i++) {
                integerList.add(i);
            }

            CustomList<String> stringList = new CustomArrayList<>();
            stringList.add("Peggy");
            stringList.add("Thom");

            assertTrue(integerList.hasElement(1));
            assertTrue(stringList.hasElement("Thom"));
        }
    }

    @Nested
    @DisplayName("size()")
    public class MethodSize {
        @Test
        public void getListSize_whenValuesRemove() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 100; i++) {
                integerList.add(i);
            }

            assertEquals(100, integerList.size());

            integerList.remove(20);

            assertEquals(99, integerList.size());

            integerList.removeAll();

            assertEquals(0, integerList.size());
        }

        @Test
        public void getListSize_whenValuesAdd() {
            CustomList<Integer> integerList = new CustomArrayList<>();

            for (int i = 0; i < 100; i++) {
                integerList.add(i);
            }

            assertEquals(100, integerList.size());

            integerList.add(200);

            assertEquals(101, integerList.size());
        }
    }
}