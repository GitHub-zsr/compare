import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CompareClass
{
	/*自定义工具类，用于比较同一类的两个不同实例的属性值是否相同，
	  并返回两个实例中的相同属性的不同属性值。
	*/
	public String void compareClass(Object o1, Object o2, Class c)
        {
            Field[] fields = c.getDeclaredFields();
            //不用get()方法是因为get只能得到public修饰的属性。
            for (Field f : fields)
            {
                try {
                    String name = f.getName();
                    //拼接属性的get方法名
                    String getMethod = "get"+ name.substring(0, 1).toUpperCase()+name.substring(1,name.length());
                    //得到方法
                    Method method = c.getMethod(getMethod);
                    //获取方法的返回参数
                    Object invoke1 =  method.invoke(o1);
                    Object invoke2 =  method.invoke(o2);
                    String s = "";
                    //不为空则比较两属性的值是否相同
                    if (invoke1!=null && invoke2!=null)
                    {
                        String value = f.getAnnotation(Ann.class).value();
                        //Ann.class 自定义注解类，标注在类属性上，作用：类属性的注释
                        s += invoke1.toString().equals(invoke2.toString()) ? "" : "修改前："+invoke1.toString()+"，修改后："+invoke2.toString()+"，修改项："+value + "。";
                    }
                } catch (NoSuchMethodException e) {
                	//类的一些属性并没有get方法，如序列化，所以会抛出异常
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return s;
            }
        }
}
