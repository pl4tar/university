#include <iostream>
#include <queue>
#include <climits>
#include <time.h>

using namespace std;

class Graph {
private:
    int** matrix;
    int N;

public:
    Graph(int n);
    void CreateGraph();
    void CreateRandGraph();
    void PrintGraph();
    void ShortestDistances(int start);
    ~Graph();
};

Graph::Graph(int n) : N(n) {
    matrix = new int*[n];
    for (int i = 0; i < n; ++i) {
        matrix[i] = new int[n];
    }
}

Graph::~Graph() {
    for (int i = 0; i < N; ++i) {
        delete[] matrix[i];
    }
    delete[] matrix;
}

void Graph::CreateRandGraph(){
    for(auto i = 0; i < N; ++i){
        for (auto j = 0; j < N; ++j)
            matrix[i][j] = rand() % 15 > 2;
    }
}

void Graph::CreateGraph(){
    int tempmatrix[5][5] = {
            {0, 1, 1, 0, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 1, 0, 1, 0}
    };

    for(auto i = 0; i < N; ++i){
        for (auto j = 0; j < N; ++j){
            matrix[i][j] = tempmatrix[i][j];
        }
    }
}

void Graph::PrintGraph(){
    cout << "\nМатрица смежности (ориентированный граф):";
    cout << "\n      1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 0";
    cout << "\n-----------------------------------------------------------------";
    for (auto i = 0; i < N; ++i)
    {
        if (i+1 >= 10) cout << "\n "<< i+1 << "| ";
        else cout << "\n "<< i+1 << " | ";
        for(auto j = 0; j < N; ++j)
            cout << " " << matrix[i][j] << " ";
    }
}

void Graph::ShortestDistances(int start) {
    queue<int> q;
    int* distances = new int[N];

    for (int i = 0; i < N; ++i) {
        distances[i] = INT_MAX;
    }

    distances[start] = 0;
    q.push(start);

    while (!q.empty()) {
        int current = q.front();
        q.pop();

        for (int i = 0; i < N; ++i) {
            if (matrix[current][i] && distances[i] == INT_MAX) {
                distances[i] = distances[current] + 1;
                q.push(i);
            }
        }
    }

    cout << "Расстояния от вершины " << start + 1 << " до остальных вершин:\n";
    for (int i = 0; i < N; ++i) {
        cout << "\nВершина " << i + 1 << ": " << distances[i] << " ";
    }

    delete[] distances;
}

int main() {
    int n, choose;
    setlocale(LC_ALL, "Russian");
    srand(time(nullptr));

    cout << "Выберите режим:\n 1 - автогенерация\n 2 - готовые данные\n выбор: ";
    cin >> choose;

    if (choose == 1) {
        // Генерация матрицы смежности неориентированного графа
        cout << "Введите количество вершин (максимум 20): ";
        cin >> n;
        Graph A(n);
        A.CreateRandGraph();
        A.PrintGraph();

        int startVertex;
        cout << "\nВведите стартовую вершину (1-" << n << "): ";
        cin >> startVertex;

        A.ShortestDistances(startVertex - 1);
    } else if (choose == 2) {
        Graph A(5);
        A.CreateGraph();
        A.PrintGraph();

        int startVertex;
        cout << "\nВведите стартовую вершину (1-5): ";
        cin >> startVertex;

        A.ShortestDistances(startVertex - 1);
    }

    return 0;
}
