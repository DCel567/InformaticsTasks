#include <iostream>
#include <string>
#include <vector>
#include <functional>
#include <math.h>
using namespace std;

const double pi = 3.14159265;

template<typename Func>
int f(Func func) {
	int negatives = 0;
	for (double i = -(double)2*pi; i <= (double)2*pi; i += pi/6) { 
		cout << func(i) << endl;
		if (func(i) < 0) negatives++;
	}
	cout << "\nNumber of negative results: " << negatives << endl;
	return negatives;
}

template<typename Func>
static void find_minmax_in_n(Func func) {
	vector<double> n;
	double k, min, max;
	cout << "Input numbers [-10; 10]: \n";
	
	while (cin >> k){
		//ввести контрл+D, чтобы закончить ввод
		n.push_back(k);
	}
	cin.clear(); cin.ignore();
	
	min = func(n[0]); max = func(n[0]);

	for (int i = 1; i < n.size(); i++) {
		if (func(n[i]) < min) min = func(n[i]);
		if (func(n[i]) > max) max = func(n[i]);
	}
	cout << "Min = " << min << "\nMax = " << max << endl;
}

template<typename Func>
double solve_equot(Func func, double a, double b) {
	double epsilon = 0.0001;
	double midpoint = 0;
	double f_a = 0, f_b = 0;

	while (b - a > epsilon) {
		f_a = func(a); f_b = func(b);
		midpoint = (a + b) / 2;
		if (f_a * func(midpoint) < 0) b = midpoint;
		else a = midpoint;
	}
	return (a + b) / 2;
}

template<typename Func>
double square_method(Func func) {
	double a = -pi, b = pi;
	int n = 10;
	double s = (func(a) + func(b)) / 2;
	double h = (b - a) / n;
	for (int i = 1; i <= n - 1; i++){
		s += func(a + i * h);
	}
	double res = h * s;
	return res;
}

bool is_palindrom(string& s) {
	bool palindrom = true;
	for (int i = 0; i < s.length() / 2; i++) {
		if (s[i] != s[s.length() - i - 1]) palindrom = false;
	}
	return palindrom;
}

void detect_word(vector<string>& v) {
	int five_letters = 0, palindroms = 0;
	for (int i = 0; i < v.size(); i++) {
		if (v[i].length() == 5) five_letters++;
		if (is_palindrom(v[i])) palindroms++;
		if (v[i][0] == 'W') cout << v[i] << " ";
	}
	cout << "\n" << "Amount of five-letters words: " << five_letters;
	cout << "\n" << "Amount of palindroms: " << palindroms << endl;
}

int main()
{
	cout << "1)\n";
	auto a = [](double x) {
		return sin(x) + 1;
	};

	auto b = [](double x) {
		return pow(x / pi - 1, 2);
	};

	auto c = [](double x) {
		return -pow(x / pi, 2) - 2 * x + 5 * pi;
	};

	auto d = [](double x) {
		return ((double)1 / 2 * pow(cos(x), 2)) + 1;
	};

	vector<function<double(double)>> funcs;
	funcs.push_back(a); funcs.push_back(b);
	funcs.push_back(c); funcs.push_back(d);
	
	int negatives = 0;

	for (int i = 0; i < funcs.size(); i++) {
		negatives += f(funcs[i]);
		cout << "---------------\n";
	}
	cout << "Total count of negative numbers is " << negatives << "\n\n";

	cout << "2)\n";

	cout << "Minmax in first:\n";
	find_minmax_in_n(a);
	cout << "Minmax in second:\n";
	find_minmax_in_n(b);
	cout << "Minmax in third:\n";
	find_minmax_in_n(c);
	cout << "Minmax in fourth:\n";
	find_minmax_in_n(d);

	cout << "\n4)\n";

	auto first_eq = [](double x) {
		return x * sin(x) - 0.5;
		//[0;pi]
	};
	auto second_eq = [](double x) {
		return log10(x * x - 3 * x + 2);
		//[0;0.9]
	};
	auto third_eq = [](double x) {
		return log10(x * x - 3 * x + 2);
		//[2.1;5]
	};
	auto forth_eq = [](double x) {
		return 0.5 * tan((double)2 / 3 * (x + pi / 4)) - 1;
		//[pi;2*pi]
	};

	cout << "First: " << solve_equot(first_eq, 0, pi) << endl;
	cout << "Second: " << solve_equot(second_eq, 0, 0.9) << endl;
	cout << "Third: " << solve_equot(third_eq, 2.1, 5) << endl;
	cout << "Forth: " << solve_equot(forth_eq, pi, 2 * pi) << endl;

	cout << "\n5)\n";

	auto integralA = [](double x) {
		return (double)2 * sin(x) + 1;
	};
	auto integralB = [](double x) {
		return -pow(2 / pi, 2) - 2 * x + 5 * pi;
	};
	auto integralC = [](double x) {
		return (double)1 / 2 * pow(cos(x), 2) + 1;
	};

	cout << "First: " << square_method(integralA) << endl;
	cout << "Second: " << square_method(integralB) << endl;
	cout << "Third: " << square_method(integralC) << endl;

	cout << "\n6)\n";
	vector<string> words = {"Wow", "arnra", "symphony"};
	detect_word(words);
}