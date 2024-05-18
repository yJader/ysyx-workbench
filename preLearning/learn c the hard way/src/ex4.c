#include <stdio.h>

/* Warning: This program is wrong on purpose. */

int main()
{
    int age = 10;
    int height;

    printf("int size: %d\n", sizeof(int));
    printf("int size: %d\n", sizeof(long long));

    printf("I am %c years old.\n");
    printf("I am %d years old.\n");
    printf("I am %d inches tall.\n", height);

    return 0;
}