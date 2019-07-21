package Free;

import java.nio.file.FileAlreadyExistsException;

import javax.crypto.ExemptionMechanismException;

public class TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// static 함수 호출
		System.out.println(func(1, 2));
//		System.out.println(<Integer>func(1, 2)); : 에러
		
		// dynamic 함수 호출
		Derived d = new Derived();
		System.out.println(d.func(1.1, 2.2));
		System.out.println(d.<Double> func(1.1, 2.2));
	}
	
	static <T> T func(T a, T b) {
		return a;
	}

	static class Base {
		public String toString() {
			return "Base Class";
		}
	}
	static class Derived extends Base {
		public String toString() {
			return "Derived Class";
		}
		public <T> T func(T a, T b) {
			return b;
		}
	}
	
	/**
	 * 
	 * @throws ExemptionMechanismException
	 * @throws FileAlreadyExistsException
	 */
	static void func2() throws ExemptionMechanismException, FileAlreadyExistsException {
		int n = 0;
		if(n == 0) {
			throw new FileAlreadyExistsException("sa");
		} else {
			throw new ExemptionMechanismException();
		}
	}
}
