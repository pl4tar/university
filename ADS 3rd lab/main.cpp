#include <iostream>
#include "BinaryTree.h"
int N = 10;

int main() {
    srand(time(0));
    BinaryTree<int> A(N);
    std::cout << "A: ";
    A.printTree();
    BinaryTree<int> B(N);
    std::cout << "B: ";
    B.printTree();
    std::cout << "=== A xor B ===" << "\n";
    (A^B).printTree();
    std::cout << "\n";
    BinaryTree<int> C(N);
    std::cout << "C: ";
    C.printTree();
    BinaryTree<int> D(N);
    std::cout << "D: ";
    D.printTree();
    std::cout << "=== (C + D) ===" << "\n";
    (C + D).printTree();
    std::cout << "\n";
    std::cout << "=== A xor B / (C + D) ===" << "\n";
    BinaryTree<int> L;
    L = (C + D);
    ((A ^ B) / L).printTree();
    std::cout << "\n";
    BinaryTree<int> E(N);
    std::cout << "E: ";
    E.printTree();
    std::cout << "=== A xor B / (C + D) / E ===" << "\n";
    ((A ^ B) / L / E).printTree();
    std::cout << "\n";

    BinaryTree<int> F(N);
    std::cout << "F: ";
    F.printTree();

    BinaryTree<int> R(N);
    std::cout << "R: ";
    R.printTree();

    std::cout << "=== F.MERGE(R) ===" << "\n";
    F.MERGE(R);
    F.printTree();
    std::cout << "\n";

    std::cout << "=== F.CONCAT(R) ===" << "\n";
    F.CONCAT(R);
    F.printTree();
    std::cout << "\n";

    std::cout << "=== F.SUBSTR(R, 4) ===" << "\n";
    F.SUBSTR(R, 4);
    F.printTree();
    std::cout << "\n";

    return 0;
}
