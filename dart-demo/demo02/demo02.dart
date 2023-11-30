void main() {
  List myList = ['1', '2'];
  myList.removeAt(1);
  myList.fillRange(1, 2, "aaa");
  myList.insert(1, "e");
  String printUserInfo(String username, [age]) {
    if (age != null) {
      return username + String.fromCharCode(age);
    }
    return username;
  }

  printUserInfo("test");

  String printUserInfo2(String username, [String sex = "type", age]) {
    if (age != null) {
      return username + String.fromCharCode(age);
    }
    return username;
  }

  printUserInfo2("username");

  String printUserInfo3(String username, {age, String sex = "nan"}) {
    if (age == 0) {
      return username + String.fromCharCode(age);
    }
    return username;
  }

  printUserInfo3("username", age: 1);

  var fn = () {};
  fn();
}
