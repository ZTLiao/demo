void main() {
  bool flag = !(1 == 1);
  var sex = flag ? '男' : '女';
  switch (sex) {
    case '男':
      {
        break;
      }
    case '女':
      {
        break;
      }
    default:
      {
        print("default...");
        break;
      }
  }
  var a;
  var b = a ?? 10;
  print(b);

  String str = "123";
  var myNum = int.parse(str);
  print(myNum is int);

  try {
    double c = 1 / 0;
  } catch (err) {
    print(err);
  }
}
