import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * JAVA中循环删除list中元素的方法总结
 */
public class TestDeleteList {
	
	/**
	 * for循环遍历list--正序删除
	 */
	public static void delete_1(List<Integer> list){
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) % 2 == 1) {
				Integer data = list.get(i);
				list.remove(i);
				i--;// 重点：list.remove(i)后，后台元素后向前移动一位
				System.out.println("当前删除元素：" + data);
				System.out.println("删除后list大小：" + list.size());
			}
		}
	}
	
	/**
	 * for循环遍历list--倒序删除
	 */
	public static void delete_2(List<Integer> list){
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) % 2 == 1) {
				Integer data = list.get(i);
				list.remove(i);
				System.out.println("当前删除元素：" + data);
				System.out.println("删除后list大小：" + list.size());
			}
		}
	}
	
	/**
	 * iterator遍历--删除
	 */
	public static void delete_3(List<Integer> list){
		Iterator<Integer> it = list.iterator();
		while (it.hasNext()) {
			Integer data = it.next();
			if (data % 2 == 1) {
				it.remove();// 注意，这里不能用list.remove();
				System.out.println("当前删除元素：" + data);
				System.out.println("删除后list大小：" + list.size());
			}
		}
	}
	
	/**
	 * 增强for循环（foreach循环）--删除--报错
	 * 删除元素后继续循环会报错误信息ConcurrentModificationException
	 */
	public static void delete_wrong(List<Integer> list){
		for (Integer data : list) {
			list.remove(data);
		}
	}
	
	/**
	 * 初始化list
	 */
	public static List<Integer> initList(){
		List<Integer> data_list = new ArrayList<Integer>();
		data_list.add(2);
		data_list.add(7);
		data_list.add(8);
		data_list.add(3);
		data_list.add(9);
		data_list.add(4);
		data_list.add(1);
		data_list.add(6);
		data_list.add(5);
		return data_list;
	}

	public static void main(String[] args) {
		List<Integer> data_list = initList();
		//List是有序的,是以数组的存储方式进行存储--测试
		System.out.println("测试--List是有序的---");
		for (Integer data : data_list) {
			System.out.println("当前元素：" + data);
		}
		
		System.out.println("\n测试--for循环遍历list--正序删除---");
		delete_1(initList());
		System.out.println("\n测试--for循环遍历list--倒序删除---");
		delete_2(initList());
		System.out.println("\n测试--iterator遍历--删除---");
		delete_3(initList());
		System.out.println("\n测试--增强for循环（foreach循环）--删除--报错---");
		delete_wrong(initList());
	}
	
}
