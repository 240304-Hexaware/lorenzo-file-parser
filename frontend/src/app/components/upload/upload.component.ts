import { Component, ElementRef, ViewChild } from '@angular/core';
import { FileService } from '../../services/file/file.service';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { FormControl, FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { NgIf, AsyncPipe } from '@angular/common';



@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [ 
    NgIf,
    FormsModule,
     MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    AsyncPipe
   ],
  templateUrl: './upload.component.html',
  styleUrl: './upload.component.css'
})
export class UploadComponent {

  @ViewChild('specFileInput') specFileInput!: ElementRef;
  @ViewChild('flatFileInput') flatFileInput!: ElementRef;
  specFile: File | null = null;
  flatFile: File | null = null;
  specName: string = "";
  fileContent$?: Observable<string>;
  specFileContent$?: Observable<string>;

  constructor(private fileService: FileService) { }

  onSubmitSpec(event: Event) {
    event.preventDefault();
    if (!this.specFile) {
      console.error('Please attach a specification file.');
      alert("Please attach a specification file");
      return;
    }
    if (this.specName.length < 3) {
      console.error('Specification name must be at least 3 characters.');
      alert("Specification name must be at least 3 characters");
      return;
    }
    this.uploadSpecFile();
  }
  
  onSubmitFlat(event: Event) {
    event.preventDefault();
    if (!this.flatFile) {
      console.error('Please attach a flat file.');
      alert("Please attach a flat file");
      return;
    }
    this.uploadFlatFile();
  }
  

  onSpecFileSelected(event: Event) {
    const inputElement = event.target as HTMLInputElement; 
    if (inputElement && inputElement.files && inputElement.files.length > 0) {
      this.specFile = inputElement.files[0];
      this.specFileContent$ = this.fileService.parseFileContent(this.specFile);
    }
  }

  onFlatFileSelected(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    if(inputElement && inputElement.files && inputElement.files.length > 0){
      this.flatFile = inputElement.files[0];
      this.fileContent$ = this.fileService.parseFileContent(this.flatFile);
    }
  }


  private uploadSpecFile() {
    if(!this.specFile || !this.specName){
      return;
    }
    this.fileService.uploadSpecFile(this.specFile, this.specName).subscribe(
      response => {
        alert("specification file uploaded successfully");
        this.resetForm();
      },
      error => {
        console.error('Error uploading specification file:', error);
        alert("Error uploading specification file");
      }
    );
  }

  private uploadFlatFile() {
    this.fileService.uploadFlatFile(this.flatFile)
      .then(message => {
        alert("flat file saved successfully");
        this.resetForm();
      })
      .catch(errorMessage => {
        console.error(errorMessage);
      });
  }



  private resetForm() {
    this.specFile = null;
    this.flatFile = null;
    this.specName = "";
    this.specFileInput.nativeElement.value = '';
    this.flatFileInput.nativeElement.value = '';
  }
}
