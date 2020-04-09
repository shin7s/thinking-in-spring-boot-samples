package thinking.in.spring.boot.samples.spring5.types;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;

public class MyEvent {
	
	public static void main(String[] args) {
		PayloadApplicationEvent<Object> payload = new PayloadApplicationEvent<>(Thread.currentThread(), new MyEvent());
		
		ResolvableType evebType = ((PayloadApplicationEvent<Object>) payload).getResolvableType();
		
		Object source = evebType.getSource();
		System.out.println(source);
		System.out.println(evebType.getRawClass());
		System.out.println(evebType.getGenerics()[0]);
//		System.out.println(resolvableType.);
		
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Class MyEvent";
	}
	
	

}
