import 'package:amapbus_example/ui/search_bus_line.dart';
import 'package:amapbus_example/ui/search_bus_station.dart';
import 'package:amapbus_example/ui/search_bus_transfer.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int _currentPage;
  PageController _pageController;
  @override
  void initState() {
    super.initState();
    _pageController = new PageController();
    _currentPage = 0;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: PageView(
          controller: _pageController,
          children: <Widget>[
            SearchBusStation(),
            SearchBusLine(),
            SearchBusTransfer()
          ],
          physics: NeverScrollableScrollPhysics(),
          onPageChanged: (index){

          },
        ),
        bottomNavigationBar: BottomNavigationBar(
          currentIndex: _currentPage,
          type: BottomNavigationBarType.fixed,
          items: [
            BottomNavigationBarItem(
              icon: Icon(Icons.departure_board),
              title: Text("站点"),
            ),
            BottomNavigationBarItem(
                icon: Icon(Icons.directions_bus),
                title: Text("线路")
            ),
            BottomNavigationBarItem(
                icon: Icon(Icons.all_inclusive),
                title: Text("换乘")
            ),
          ],
          onTap: (index){
            _currentPage = index;
            setState(() {
              _pageController.jumpToPage(index);
            });

          },
        ),
      )
    );
  }

}
