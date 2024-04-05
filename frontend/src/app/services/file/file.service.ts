import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, map, Observable, Observer, switchMap, throwError } from 'rxjs';
import { TokenService } from '../token/token.service';
import axios from 'axios';
import { SpecificationResponse } from '../../interfaces/specification-response';
import { ParsedResponse } from '../../interfaces/parsed-response';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private token: TokenService) { }


  getFlatFiles(): Observable<string[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<string[]>(`${this.apiUrl}/flatfile`, { headers });
  }

  getSpecFiles(): Observable<SpecificationResponse[]> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<SpecificationResponse[]>(`${this.apiUrl}/specifications/all/${this.token.getUserId()}`, { headers });
  }

  getSpecificationContent(filename: string): Observable<SpecificationResponse> {
    const headers = this.createAuthorizationHeader();
    return this.http.get<SpecificationResponse>(`${this.apiUrl}/specifications/${filename}`, { headers })
  }

  getParsedDataFromFile(flatFilePath: string): Observable<ParsedResponse[]> {
    const url = `${this.apiUrl}/parse/user/file/${this.token.getUserId()}/${encodeURIComponent(flatFilePath)}`;
    const headers = this.createAuthorizationHeader();
    return this.http.get<ParsedResponse[]>(url, { headers });
  }

  getParsedDataFromSpec(specName: string): Observable<ParsedResponse[]> {
    const headers = this.createAuthorizationHeader();
    const url = `${this.apiUrl}/parse/user/specs/${this.token.getUserId()}/${encodeURIComponent(specName)}`;
    return this.http.get<ParsedResponse[]>(url, { headers });
  }



  getFlatFileContent(filename: string) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token.getToken()}`
    });
    return this.http.get(`${this.apiUrl}/flatfile/download/${filename}`, { headers, responseType: 'text' });
  }


  getParsedDataFromFieldAndValue(specField: string, specValue: string): Observable<ParsedResponse[]> {
    const headers = this.createAuthorizationHeader();
    const url = `${this.apiUrl}/parse/user/key/value/${this.token.getUserId()}/${encodeURIComponent(specField)}/${encodeURIComponent(specValue)}`;
    return this.http.get<ParsedResponse[]>(url, { headers });
  }



  uploadSpecFile(file: File, specName: string): Observable<SpecificationResponse> {
    if (!file || !specName) {
      console.error('File and specName are required.');
      return throwError('File and specName are required.'); 
    }

    return this.parseFileContent(file).pipe(
      catchError(error => {
        console.error('Error parsing file content:', error);
        return throwError('Error parsing file content.'); 
      }),
      switchMap(content => {
        const specs = JSON.parse(content);
        const requestBody = {
          specName,
          specs
        };
        const headers = new HttpHeaders({
          'Authorization': `Bearer ${this.token.getToken()}`
        });

        return this.http.post<SpecificationResponse>(`${this.apiUrl}/specifications/upload/${this.token.getUserId()}`, requestBody, { headers }).pipe(
          catchError(error => {
            console.log(requestBody)
            console.error('Error uploading specification file:', error);
            if(error.status === 409) {
              alert("specification name already exists");
            }
            return throwError('Error uploading specification file.'); 
          })
        );
      })
    );
  }
  
  // parseFileContent(file: File): Observable<string> {
  //   return new Observable<string>(observer => {
  //     const reader = new FileReader();

  //     reader.onload = (event: any) => {
  //       observer.next(event.target.result);
  //       observer.complete();
  //     };

  //     reader.onerror = (error: any) => {
  //       observer.error(error);
  //     };

  //     reader.readAsText(file);
  //   });
  // }
  parseFileContent(file: File): Observable<string> {
    return new Observable<string>((observer: Observer<string>) => {
      const reader = new FileReader();
  
      reader.onload = (event: ProgressEvent<FileReader>) => {
        if (event.target) {
          const result = event.target.result;
          if (typeof result === 'string') {
            observer.next(result);
            observer.complete();
          } else {
            observer.error(new Error('Invalid result type'));
          }
        }
      };
  
      reader.onerror = (error: ProgressEvent<FileReader>) => {
        observer.error(error);
      };
  
      reader.readAsText(file);
    });
  }

  uploadFlatFile(file: File | null): Promise<string> {
    return new Promise((resolve, reject) => {
      if (!file) {
        reject('No file selected for upload');
        return;
      }
  
      const formData = new FormData();
      formData.append('file', file);
      const token = this.token.getToken();
  
      axios.post(`http://localhost:8080/api/flatfile/upload`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${token}`
        }
      })
      .then(response => {
        resolve('File saved successfully');
      })
      .catch(error => {
        console.error('Error uploading flat file:', error);
        reject('File could not be uploaded');
      });
    });
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = this.token.getToken();
    if (token) {
      return new HttpHeaders({ Authorization: `Bearer ${token}` });
    } else {
      return new HttpHeaders();
    }
  }
  
  parseFlatFile(flatFileName: string, specificationName: string): Observable<ParsedResponse[]> {
    const path = `/Users/user/Desktop/lorenzo-file-parser/backend/uploads/${flatFileName}`;

    const userId= this.token.getUserId();

    // Construct the request body
    const requestBody = {
      specificationName,
      path,
      userId
    };

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token.getToken()}`
    });

    return this.http.post<ParsedResponse[]>(`${this.apiUrl}/parse`, requestBody, { headers }).pipe(
      catchError(error => {
        console.error('Error parsing flat file:', error);
        return throwError('Error parsing flat file.'); // Or handle the error accordingly
      })
    );
  }


}

