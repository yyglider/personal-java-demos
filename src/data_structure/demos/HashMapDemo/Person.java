package data_structure.demos.HashMapDemo;

/**
 * Java 用自定义类型作为HashMap的键,重载hashCode()和equals()两个方法才能实现自定义键在HashMap中的查找
 */
public class Person {
    private String id;
    public Person(String id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o==null || this.getClass()!=o.getClass()) return false;

        Person person = (Person) o;
        if(id!=null ? !id.equals(person.id):person.id != null) return  false;

        return true;
    }
    @Override
    public int hashCode(){
        return id!=null?id.hashCode():0;
    }
}
