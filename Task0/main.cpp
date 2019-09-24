#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <iomanip>
using namespace std;

int count_bytes = 0;

void draw_line() {
	for (int i = 0; i < 131; i++) cout << "-";
	cout << '\n';
}

void draw_spaces(int sa) {
	for (int i = 0; i < sa; i++) cout << " ";
}

void draw_header() {

	cout << "Размер данных в файле: " << count_bytes << " байт." << endl;
	cout << "Решение вариант а) данные в string" << endl;

	draw_line();
	cout << " № " << " | " << "слово";
	draw_spaces(51);
	cout << "|";
	draw_spaces(11);
	cout << "|              Количество информации\n";
	draw_line();
	
	draw_spaces(4);
	cout << "|";
	draw_spaces(57);
	cout << "|";
	draw_spaces(11);
	cout << "|  кол-во   | байт, размер |     бит,     |      бит,    \n";

	draw_spaces(4);
	cout << "|";
	draw_spaces(57);
	cout << "|";
	cout << " палиндром | символов  | в программе  |   по Хартли  |  по Шеннону  \n";
	draw_line();
}

bool is_palindrom(string &str){
	bool flag = true;
	for (int i = 0; i < (str.length() / 2); i++) {
		if (str[i] != str[str.length() - i - 1]) {
			flag = false; break;
		}
	}
	return flag;
}

void symbol_count(string &str) {
	cout << " " << str.length();
	draw_spaces(10 - (to_string(str.length())).length());
}

void size_in_programm(string &str) {
	int took = 13 - (to_string(str.length()*2)).length();
	draw_spaces(took);
	cout << str.length()*2 << " ";
}

void size_by_Hartly(string& str) {
	bool exist = false;
	vector<char> letters;
	for (int i = 0; i < str.length(); i++) {
		exist = false;
		for (int j = 0; j < letters.size(); j++) {
			if (letters[j] == str[i]) {
				exist = true; break;
			}
		}
		if (!exist) letters.push_back(str[i]);
	}
	double res = str.length() * log2(letters.size());
	int took = 10 - to_string((int)res).length();
	draw_spaces(took);
	cout.setf(ios::fixed);
	cout << setprecision(2) << res << " ";
}

void size_by_Shennon(string& str) {
	vector<pair<char, int>> letters;
	bool exist = false;
	for (int i = 0; i < str.length(); i++) {
		exist = false;
		for (int j = 0; j < letters.size(); j++) {
			if (str[i] == letters[j].first) {
				letters[j].second++;
				exist = true;
				break;
			}
		}
		if (!exist) letters.push_back(pair<char, int>(str[i], 1));
	}
	double res = 0;
	for (int i = 0; i < letters.size(); i++) {
		res += ((double)letters[i].second / (double)str.length())*log2(1/((double)letters[i].second / (double)str.length()));
	}
	cout << " ";
	cout.setf(ios::fixed);
	cout << setprecision(9) << res << endl;
}

void draw_string(string str, int num) {
	cout << " ";
	if ((num + 1) / 10 < 1) {
		cout <<  num + 1 << "  ";
	}
	else {
		cout << num + 1 << " ";
	}
	cout << "| " << str;
	draw_spaces(56 - str.length());
	cout << "|";
	if (is_palindrom(str)) cout << " +         |";
	else cout << " -         |";
	symbol_count(str);
	cout << "|";
	size_in_programm(str);
	cout << "|";
	size_by_Hartly(str);
	cout << "|";
	size_by_Shennon(str);
	draw_line();
}

void write_table(vector<string> &strings) {
	for (int i = 0; i < strings.size(); i++) {
		draw_string(strings[i], i);
	}
}

int main() {
	setlocale(LC_ALL, "russian");
	ifstream ifs("TextFile1.txt");
	vector<string> strings;
	string str;
	while (ifs >> str) {
		count_bytes += str.length();
		strings.push_back(str);
	}
	draw_header();
	write_table(strings);
	
}