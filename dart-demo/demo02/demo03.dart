import 'dart:mirrors';

void main(List<String> args) {
  final a = A<B>();
  final b1 = a.getInstance();
  final b2 = a.getInstance();
  print('${b1.value}|${b1.text}|${b1.hashCode}');
  print('${b2.value}|${b2.text}|${b2.hashCode}');
}

class A<T extends B> {
  static int count = 0;
  T getInstance() {
    return reflectClass(T).newInstance(
      Symbol(''),
      ['Text ${++count}'],
      {Symbol('value'): count},
    ).reflectee;
  }
}

class B {
  final int value;
  final String text;
  B(this.text, {required this.value});
}
