import { Optional } from '@angular/core';

export class ApiResponse {
    status: number;
    message: any;
    result: any;  

    constructor(@Optional() status: number, message: any, result: any){
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
