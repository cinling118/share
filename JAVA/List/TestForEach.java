import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 集合遍历
 */
public class TestForEach {
	public static void main(String[] args) {
		testListForEach();
		System.out.println("\n---Map<K,V>循环---");
		testMapForEach();
	}
	
	/**
	 * List<E>循环
	 */
	public static void testListForEach(){
		List<String> list = Arrays.asList("1","2","3");
		//for循环
		System.out.println("---for循环---");
		for (int i=0;i< list.size();i++){
		    System.out.println(list.get(i));
		}

		//增强for循环
		System.out.println("\n---增强for循环---");
		for(String str : list){
		    System.out.println(str);
		}
		
		//迭代器
		System.out.println("\n---迭代器---");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()){
		    System.out.println(iterator.next());
		}
		
		//forEach遍历--JAVA8新特性
		System.out.println("\n---forEach遍历--JAVA8新特性---");
		list.forEach(new Consumer<String>() {
		    @Override
		    public void accept(String s) {
		        System.out.println(s);
		    }
		});
		//forEach+Lambda表达式--JAVA8新特性
		System.out.println("\n---forEach+Lambda表达式--JAVA8新特性---");
		list.forEach(str->{System.out.println(str);});
		//当循环体中只有一行代码时，可以不用写"{}"
//		list.forEach(str->System.out.println(str));
	}
	
	/**
	 * Map<K,V>循环
	 */
	public static void testMapForEach(){
		Map<String,String> map = new HashMap<>();
		map.put("id","11");
		map.put("name","zhangsan");
		map.put("age","30");
		//entrySet
		for (Map.Entry<String,String> entry : map.entrySet()){
		    System.out.println("k=" + entry.getKey() + ",v=" + entry.getValue());
		}
		//keySet
		for (String key : map.keySet()){
		    System.out.println("k=" + key + ",v=" + map.get(key));
		}
		//迭代器
		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()){
		    Map.Entry<String, String> entry = iterator.next();
		    System.out.println("k=" + entry.getKey() + ",v=" + entry.getValue());
		}
		//values
		for (String v : map.values()){
		    System.out.println("v=" + v);
		}
		
		//forEach遍历--JAVA8新特性
		map.forEach(new BiConsumer<String, String>() {
		    @Override
		    public void accept(String k, String v) {
		        System.out.println("k=" + k + ",v=" + v);
		    }
		});
		
		//forEach遍历+Lambda表达式--JAVA8新特性
		map.forEach((k,v)->{System.out.println("k=" + k + ",v=" + v);});
		//当循环体中只有一行代码时，可以不用写"{}"
//		map.forEach((k,v)->System.out.println("k=" + k + ",v=" + v));
	}
}
