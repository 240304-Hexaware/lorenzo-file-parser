import { Component } from '@angular/core';
import { FileService } from '../../services/file/file.service';
import { KeyValuePipe, NgFor, NgIf, JsonPipe } from '@angular/common';
import { KeyValue } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';


@Component({
  selector: 'app-parse',
  standalone: true,
  imports: [ NgFor, NgIf, KeyValuePipe, JsonPipe, MatButtonModule ],
  templateUrl: './parse.component.html',
  styleUrl: './parse.component.css'
})
export class ParseComponent {
  flatFiles: any[] = [];
  specifications: any[] = [];
  selectedFlatFile: any = null;
  selectedSpecification: any = null;
  parsingResults: any  = null;
  isParsed: boolean = false;
  flatFileContent: string | null = null;
  specificationContent: any | null = null;
  

  constructor(
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    this.loadFlatFiles();
    this.loadSpecifications();
  }

  loadFlatFiles(): void {
    this.fileService.getFlatFiles().subscribe(flatFiles => {
      this.flatFiles = flatFiles;
    });
  }

  loadSpecifications(): void {
    this.fileService.getSpecFiles().subscribe(specifications => {
      this.specifications = specifications;
    });
  }

  selectFlatFile(flatFile: any): void {
    if(this.selectedFlatFile == flatFile) {
      return;
    }
    this.selectedFlatFile = flatFile;
    this.getFileContent(flatFile);
  }

  toggleSection() {
    this.selectedFlatFile = !this.selectedFlatFile;
  }

  getFileContent(flatFile: string): void {
    this.fileService.getFlatFileContent(flatFile).subscribe(
        content => {
            this.flatFileContent = content;
        },
        error => {
            console.error('Error retrieving file content:', error);
        }
    );
  }

  getSpecificationContent(specName: string): void {
    this.fileService.getSpecificationContent(specName).subscribe(
      content => {
          this.specificationContent = content;
      },
      error => {
          console.error('Error retrieving file content:', error);
      }
  );
  }

  selectSpecification(specification: any): void {
    if(this.selectedSpecification ==  specification) {
      return;
    }
    this.selectedSpecification = specification;
    this.getSpecificationContent(specification.specName);
    console.log(this.selectedSpecification);
  }

  // parseFlatFile(): void {
  //  this.fileService.parseFlatFile(this.selectedFlatFile, this.selectedSpecification.specName).subscribe(parsed =>{
  //   console.log(parsed);
  //   this.isParsed = true;
  //   this.parsingResults.push(parsed);
  //  });
  // }
  parseFlatFile(): void {
    this.fileService.parseFlatFile(this.selectedFlatFile, this.selectedSpecification.specName).subscribe(parsedList => {
      console.log(parsedList);
      this.isParsed = true;
      this.parsingResults = parsedList; 
    });
  }

  reset(): void{
    this.selectedFlatFile = null;
    this.selectedSpecification = null;
    this.isParsed = false;
    this.flatFileContent = null;
    this.specificationContent = null;
    this.parsingResults = null;
  }
  
}
