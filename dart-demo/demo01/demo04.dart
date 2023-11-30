void main() {
  var str1 = 'this is str1';
  var str2 = "this is str2";
  print(str1);
  print(str2);
  String str3 = 'this is str3';
  print(str3);
  String str4 = '''
        this is str4
        this is str4
  ''';
  print(str4);
  var l2 = <String>["1234"];
  print(l2);
  var l3 = [1, true, ""];
  l3.add(33);
  print(l3);
  var l4 = {1, 2, 3, 4};
  print(l4);
  var l5 = List.filled(2, "");
  print(l5.length);
  print(l5[0]);
  var l6 = List<String>.filled(2, "1");
  print(l6);
  var person = {"name": "zhangsan", "age": 20};
  print(person);
  print(person["name"]);
  var p = new Map();
  p["name"] = "lisi";
  p["age"] = 200;
  p["work"] = [1, 2, 3];
  print(p);
  if (p is String) {
    print("this is string");
  } else {
    print("this is not string");
  }
}
