void main() {
  int a = 13;
  int b = 5;
  print(a + b);
  print(a - b);
  print(a * b);
  print(a / b);
  print(a % b);
  print(a ~/ b);
  var c = a * b;
  print('======');
  print(c);
  print(a == b);
  print(a != b);
  print(a > b);
  print(a < b);
  print(a >= b);
  print(a <= b);
  if (a > b) {
    print("a > b");
  } else {
    print("a < b");
  }
  bool flag = false;
  print(!flag);
  print(!flag && 1 != 1);
  print(flag || flag);

  int b1 = 6;
  b1 ??= 23;
  print(b1);
}
