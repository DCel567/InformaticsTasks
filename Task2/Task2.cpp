#include <iostream>
#include <algorithm>
#include <fstream>
#include <string>
#include <deque>
#include <stack>
#include <vector>
#include <math.h>
#include <iterator>
//#include <stdlib.h>

using namespace std;

class Point {
	double x, y;
public:
	Point() :x(0), y(0){}
	Point(double a, double b) : x(a), y(b){}
	Point(int a, int b) : x(a), y(b){}
	double get_x() { return x; }
	double get_y() { return y; }

	friend bool operator==(Point s, Point f);
	friend bool operator!=(Point s, Point f);
};

bool operator==(Point f, Point s) {
	if (f.x == s.x && f.y == s.y) return true;
	return false;
}
bool operator!=(Point f, Point s) {
	return !(f == s);
}

class Line {
	vector<Point> points;
public:
	Line(vector<Point> p) : points(p){}

	vector<Point> get_points() {
		return points;
	}

	vector<Point>::iterator iterator = points.begin();

	vector<Point>::iterator begin() { return points.begin(); }
	vector<Point>::iterator end() { return points.end(); }

	double length() {
		double len = 0;
		if (points.size() <= 1) return 0;
		for (int i = 0; i < points.size()-1; i++) {
			len += sqrt(pow(points[i + 1].get_x() - points[i].get_x(), 2) +
				pow(points[i + 1].get_y() - points[i].get_y(), 2));
		}
		return len;
	}

	int node_amount() { return points.size(); }
	bool is_closed() { return (points[0] == points[points.size() - 1]) ? true : false; }
};

class Humans {
private:
	string sname;
	string fname;
	int birth_year;
	bool is_male;
public:
	Humans(string sn, string fn, int by, char s){
		sname = sn; fname = fn; birth_year = by;
		s == 'm' ? is_male = 1 : is_male = 0;
	}
	Humans() {
		sname = "Unset"; fname = "User"; birth_year = 0; is_male = 1;
	}

	void print_human(){
		cout << sname << " " << fname << " " << birth_year << " " << (is_male ? "male" : "female") << endl;
	}

	int age() { return 2019 - birth_year; }
};

void read_file(stack<Humans> &st, deque<Humans> &dq) {
	string fileName = "data.txt";
	ifstream ifs(fileName);
	string fn, sn;
	int by = 0;
	char s;

	while (ifs >> sn) {
		ifs >> fn >> by >> s;
		st.push(Humans{ sn, fn, by, s });
		dq.push_back(Humans{ sn, fn, by, s });
	}
}

void cout_under40_first(stack<Humans> &st, deque<Humans>& dq) {
	stack<Humans> tmp;
	cout << "Stack:\n";
	while (!st.empty()) {
		if (st.top().age() >= 40) {
			tmp.push(st.top()); st.pop();
		}
		else {
			st.top().print_human();
			st.pop();
		}
	}
	while (!tmp.empty()) {
		tmp.top().print_human();
		tmp.pop();
	}

	cout << "\nDeque:\n";
	while (!dq.empty()) {
		if (dq.front().age() >= 40) {
			tmp.push(dq.front()); dq.pop_front();
		}
		else {
			dq.front().print_human();
			dq.pop_front();
		}
	}
	while (!tmp.empty()) {
		tmp.top().print_human();
		tmp.pop();
	}
}

int M(int a, int b) {
	return a < b ? b : a;
}

int m(int a, int b) {
	return a < b ? a : b;
}

int think_of_m(stack<char> &stat) {
	if (isdigit(stat.top())) {
		return (int)stat.top()-'0'; 
	}
	if (stat.top() == 'M') {
		stat.pop(); stat.pop();
		int first = think_of_m(stat);
		stat.pop(); stat.pop();
		int second = think_of_m(stat);
		stat.pop();
		return (first > second) ? first : second;
	}
	if (stat.top() == 'm') {
		stat.pop(); stat.pop();
		int first = think_of_m(stat);
		stat.pop(); stat.pop();
		int second = think_of_m(stat);
		stat.pop();
		return (first < second) ? first : second;
	}
}

bool sort_by_length(string s1, string s2) {
	if (s1.length() < s2.length()) return true;
	return false;	
}

bool sort_by_big_letters(string s1, string s2) {
	int first = 0, second = 0;
	for (auto x : s1) if (x >= 'A' && x <= 'Z') first++;
	for (auto x : s2) if (x >= 'A' && x <= 'Z') second++;
	return first > second ? true : false;
}

int main()
{
	{
		//1
		int num = 0;

		stack<int> st;
		deque<int> dq;

		cout << "1) Input a number: ";
		cin >> num; cout << endl;
		while (num > 0) {
			st.push(num % 10);
			dq.push_back(num % 10);
			num /= 10;
		}

		cout << "Stack:     Deque:\n";
		while(!st.empty()) {
			cout << st.top() << "          " << dq.front() << endl; 
			st.pop(); dq.pop_front();
		}
		cout << '\n';
	}

	{
		//2
		deque<Humans> dq;
		stack<Humans> st;

		cout << "2) Data is in file.\n";
		read_file(st, dq);
		cout_under40_first(st, dq);

		cout << '\n';
	}
	
	{
		//3
		stack<char> symbols;
		string formula;
		int circleBrackets = 0, squareBrackets = 0, figureBrackets = 0;
		cout << "3) Input your statement:\n";
		cin >> formula;
		for (int i = 0; i < formula.length(); i++) {
			symbols.push(formula[i]);
		}
		while (!symbols.empty()) {
			if (symbols.top() == '(') circleBrackets++;
			if (symbols.top() == ')') circleBrackets--;
			if (symbols.top() == '[') squareBrackets++;
			if (symbols.top() == ']') squareBrackets--;
			if (symbols.top() == '{') figureBrackets++;
			if (symbols.top() == '}') figureBrackets--;
			symbols.pop();
		}
		if (circleBrackets != 0 || squareBrackets != 0 || figureBrackets != 0) cout << "Brackets are not balanced.\n";
		else cout << "Brackets balanced.\n";
		cout << endl;
	}

	{
		//4
		stack<char> statement;
		string formula;
		
		cout << "4) Input your statement:\n";
		cin >> formula;
		for (int i = 0; i < formula.length(); i++) {
			statement.push(formula[formula.length() - i - 1]);
		}
		cout << "Answer is " << think_of_m(statement);
	}

	{
		//5
		cout << "5)\n";
		vector<string> v = { "aAa", "aaAaAa", "bBbBbB", "b", "hello", "little kitty", "convenience" };
		sort(v.begin(), v.end(), sort_by_length);
		for (auto x : v) cout << x << endl;
		cout << endl;

		sort(v.begin(), v.end());
		for (auto x : v) cout << x << endl;
		cout << endl;

		sort(v.begin(), v.end(), sort_by_big_letters);
		for (auto x : v) cout << x << endl;
	}

	{
		//6
		cout << endl << "6)\n";
		vector<Point> ps = { Point(1, 1), Point(1, 2), Point(1, 3) , Point(1,1)};
		Line j = Line(ps);
		if (j.is_closed())
			cout << "Line length: " << j.length() << endl;
		cout << "Points:\n";
		for ( ; j.iterator < j.end(); j.iterator++) {
			cout << "(" << (*j.iterator).get_x() << ";" << (*j.iterator).get_y() << ")" << endl;
		}
	}
}