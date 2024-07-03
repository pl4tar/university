//
// Created by mac on 19.05.2024.
//

#ifndef ARTEM_LABA07_BINARYTREE_H
#define ARTEM_LABA07_BINARYTREE_H


#include <iostream>
#include <stack>
#include <string>
#include <cstring>
int MAXNUM = 200;
template <typename T>

class BinaryTree {
private:
    struct Node {
        T key;
        int size; // мощность поддерева
        Node* left;
        Node* right;

        Node(const T k) {
            key = k;
            size = 1;
            left = nullptr;
            right = nullptr;
        }

    };
    Node* root;
    int len;
    char** SCREEN;
    int maxrow, offset, maxcol;
    void clrscr() {
        for(int i = 0; i < maxrow; i++) {
            memset(SCREEN[i], '.', maxcol);
        }
    }
    int Order(int num)
    {
        int order = 0;

        while(num){
            num /= 10;

            ++order;
        }

        return order;
    }

    void ItoA(int num, char* buffer, size_t buffer_size)
    {
        int num_order = Order(num);


        buffer[num_order] = '\0';

        for(int i = num_order; i > 0; --i){
            buffer[i - 1] = '0' + num % 10;

            num /= 10;
        }

    }
public:
    BinaryTree() {
        len = 0;
        maxrow = 16;
        maxcol = 140;
        offset = 70;
        SCREEN = new char * [maxrow];
        for(int i = 0; i < maxrow; ++i) {
            SCREEN[i] = new char [maxcol];
        }
        root = nullptr;
    }

    BinaryTree(int n) {
        len = 0;
        root = nullptr;
        maxrow = 16;
        maxcol = 140;
        offset = 70;
        SCREEN = new char * [maxrow];
        for(int i = 0; i < maxrow; ++i) {
            SCREEN[i] = new char [maxcol];
        }
        while (len < n) {
            insert(rand() % (MAXNUM - 1 + 1) + 1);
        }
    }

    int Power() {
        return len;
    }

    void insert(int key) {
        if (root == nullptr) {
            root = insertRec(root, key);
        } else {
            insertRec(root, key);
        }
    }

    Node* insertRec(Node* root, int key) {
        if (root == nullptr) {
            len += 1;
            return new Node(key);
        }

        if (key <= root->key) {
            root->left = insertRec(root->left, key);
        } else if (key > root->key) {
            root->right = insertRec(root->right, key);
        }

        // обновляем размер поддерева
        root->size = 1;
        if (root->left != nullptr) {
            root->size += root->left->size;
        }
        if (root->right != nullptr) {
            root->size += root->right->size;
        }

        return root;
    }

    int getSize() {
        if (root == nullptr) {
            return 0;
        }
        return root->size;
    }

    int getSize(Node* node) {
        if (node == nullptr) {
            return 0;
        }
        return node->size;
    }

    int subtreeSize(int data) {
        Node* currentNode = root;
        int count = 0;

        while (currentNode != nullptr) {
            if (data == currentNode->key) {
                count = getSize(currentNode);
                break;
            } else if (data < currentNode->key) {
                currentNode = currentNode->left;
            } else {
                currentNode = currentNode->right;
            }
        }

        return count;
    }

    class Iterator {
    private:
        std::stack<Node *> stack;
        Node *current;
    public:
        Iterator(Node *root) {
            preOrderTravers(root);
            stack = foo(stack);
            if (!stack.empty()) {
                current = stack.top();
                stack.pop();
            } else current = nullptr;
        }
        void preOrderTravers(Node* root) {
            if (root != nullptr) {
                stack.push(root);
                preOrderTravers(root->left);
                preOrderTravers(root->right);
            }
        }

        Iterator &operator++() {
            if (!stack.empty()) {
                current = stack.top();
                stack.pop();
            } else current = nullptr;
            return *this;
        }
        Iterator &operator++(int) {
            auto res = *this;
            ++*this;
            return res;
        }

        bool operator!=(const Iterator& other) const {
            return (stack.size() != other.stack.size()) || (current != other.current);
        }
        bool operator==(const Iterator& other) const {
            return (stack.size() == other.stack.size()) && (current == other.current);
        }

        int& operator*() {
            return current->key;
        }
        std::stack<Node *> foo(std::stack<Node *>& in){
            std::stack<Node *> out;
            while(! in.empty() ){
                out.push(in.top());
                in.pop();
            }
            return out;
        }
    };

    Iterator begin() {
        return Iterator(root);
    }
    Iterator end() const {
        return Iterator(nullptr);
    }


    void printTree() {
        for (auto it = begin(); it != end(); ++it) {
            std::cout << *it << " ";
        }
        std::cout << "\n";
    }

    void MERGE(BinaryTree& other) {
        BinaryTree C;
        auto it1 = begin();
        auto it2 = other.begin();
        while (it1 != end() && it2 != other.end()) {
            C.insert(*it1);
            C.insert(*it2);
            it1++;
            it2++;
        }
        if (it1 == end()) {
            while (it2 != other.end()) {
                C.insert(*it2);
                it2++;
            }
        } else if (it2 == other.end()) {
            while (it1 != other.end()) {
                C.insert(*it1);
                it1++;
            }
        }
        root = C.root;
        len = C.len;
    }

    void CONCAT(BinaryTree& other) {
        for (auto it = other.begin(); it != other.end(); ++it) {
            insert(*it);
        }
    }

    void SUBSTR(BinaryTree& other, int n) {
        BinaryTree C;
        auto it = begin();
        for (int i = 0; i < n-1; i++) {
            C.insert(*it);
            ++it;
        }
        for (auto it = other.begin(); it != other.end(); ++it) {
            C.insert(*it);
        }
        while (it != end()) {
            C.insert(*it);
            ++it;
        }
        root = C.root;
        len = C.len;
    }


    BinaryTree operator+(BinaryTree& other) {
        BinaryTree merge;
        for (auto it = begin(); it != end(); ++it) {
            merge.insert(*it);
        }
        for (auto it = other.begin(); it != other.end(); ++it) {
            merge.insert(*it);
        }
        return merge;
    }
    void operator|=(BinaryTree& other) {
        root = (*this+other).root;
        len = (*this+other).len;
    }
    BinaryTree operator*(BinaryTree& other) {
        BinaryTree merge;
        for (auto it = begin(); it != end(); ++it) {
            for (auto it1 = other.begin(); it1 != other.end(); ++it1) {
                if (*it1 == *it) {
                    merge.insert(*it);
                }
            }
        }
        return merge;
    }
    void operator&=(BinaryTree& other) {
        root = (*this*other).root;
        len = (*this*other).len;
    }
    BinaryTree operator^(BinaryTree& other) {
        BinaryTree merge;
        bool key;
        for (auto it = begin(); it != end(); ++it) {
            key = true;
            for (auto it1 = other.begin(); it1 != other.end(); ++it1) {
                if (*it1 == *it) {
                    key = false;
                }
            }
            if (key) {
                merge.insert(*it);
            }
        }
        for (auto it = other.begin(); it != other.end(); ++it) {
            key = true;
            for (auto it1 = begin(); it1 != end(); ++it1) {
                if (*it1 == *it) {
                    key = false;
                }
            }
            if (key) {
                merge.insert(*it);
            }
        }
        return merge;
    }
    void operator^=(BinaryTree& other) {
        root = (*this^other).root;
        len = (*this^other).len;
    }
    BinaryTree operator/(BinaryTree& other) {
        BinaryTree C;
        bool key;
        for (auto it = begin(); it != end(); ++it) {
            key = true;
            for (auto it1 = other.begin(); it1 != other.end(); ++it1) {
                if (*it1 == *it) {
                    key = false;
                }
            }
            if (key) {
                C.insert(*it);
            }
        }
        return C;
    }

    void OutNodes(Node* v, int r, int c) {
        int num = v->key;
        int num_order = Order(num);

        char* str = new char[num_order + 1];

        ItoA(num, str, num_order + 1);
        if (r && c && (c<maxcol)) SCREEN[ r - 1 ][ c - 1 ] = str[0];
        if (r && c && (c<maxcol) && str[1]) SCREEN[ r - 1 ][ c ] = str[1];
        ; // вывод метки
        if (r < maxrow) {
            if (v->left) OutNodes(v->left, r + 1, c - (offset >> r)); //левый сын
            if (v->right) OutNodes(v->right, r + 1, c + (offset >> r)); //правый сын
        }

    }
    void OutTree() {
        clrscr();
        OutNodes(root, 1, offset);
        for (int i = 0; i < maxrow; i++) {
            SCREEN[i][maxcol-1] = 0;
            std::cout << '\n' << SCREEN[i];
        }
        std::cout << '\n';
    }
};


#endif //ARTEM_LABA07_BINARYTREE_H
