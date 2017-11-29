#include <stdio.h>
void main(){
	int n;
	scanf("%d",&n);
	for(int i=0;i<n;i++){
		for(int k=0;k<29-i;k++){
			printf(" ");	
		}
		for(int j=0;j<2*n-1;j++){
			printf("*");
		}
	
	}
	
}
