package _20_문자열_검색;

public class 단순_검색 {

}

/*
#include<stdio.h>

int length(char *);

int main(void)
{
    const int strSize = 100, targetSize = 100;
    char str[strSize], target[targetSize];

    printf("--- 문자열을 포함하는지 확인하는 프로그램 입니다 ---\n");

    printf("원본 문자열 입력: ");
    scanf("%s", str);
    printf("검정할 문자열 입력: ");
    scanf("%s", target);
    int i, j;
    int exist;
    for(i = 0; i <= (length(str) - length(target)); i++)
    {
        exist = 1;
        for(j = i; j < (i + length(target)); j++)
            if(target[j - i] != str[j])                                                                          
            {
                exist = 0;
                break;
            }
        if(exist)
            break;
    }

    printf("%s\n", exist? "exist":"not exist");

    return 0;
}

int length(char *str)
{
  int leng = 0;;
  while(*(str + leng) != NULL)
    leng++;

  return leng;
}
*/