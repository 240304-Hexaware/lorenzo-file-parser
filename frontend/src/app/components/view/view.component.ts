import { Component } from '@angular/core';
import { FileService } from '../../services/file/file.service';
import { JsonPipe, KeyValuePipe, NgFor, NgIf } from '@angular/common';
import { KeyValue } from '@angular/common';
import { spec } from 'node:test/reporters';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view',
  standalone: true,
  imports: [ NgFor, KeyValuePipe, NgIf, JsonPipe, FormsModule ],
  templateUrl: './view.component.html',
  styleUrl: './view.component.css'
})
export class ViewComponent {
  
  parsedFiles: any[] = [];
  flatFiles: any[] = [];
  specFiles: any[] = [];
  metadata: any[] = [];
  parsedData: any[] = [];
  parsedDataFromSpec: any[] = [];
  selectedFlatFile: string | null = null;
  selectedSpecFile: string | null = null;

  constructor(private fileService: FileService, private router: Router) { }

  ngOnInit(): void {
    this.getFlatFiles();
    this.getSpecFiles();
  }

  getFlatFiles() {
    this.fileService.getFlatFiles().subscribe(files => {
      this.flatFiles = files;
    });
  }

  getSpecFiles() {
    this.fileService.getSpecFiles().subscribe(files => {
      this.specFiles = files;
    });
  }

  getParsedDataFromFile(flatFile: string): void {
    if(this.selectedFlatFile == flatFile) {
      this.selectedFlatFile = null;
      return;
    }
    this.selectedFlatFile = flatFile;
    this.fileService.getParsedDataFromFile(flatFile).subscribe(
      parsedData => {
        this.parsedData = parsedData;
      },
      error => {
        console.error('Error fetching parsed data:', error);
      }
    );
  }

  redirectToQuery() {
    this.router.navigate(['/view/query']);
  }

  getParsedDataFromSpec(specName: string): void {
    if(this.selectedSpecFile == specName){
      this.selectedSpecFile = null;
      return;
    }
    this.selectedSpecFile = specName;
    this.fileService.getParsedDataFromSpec(specName).subscribe(
      parsedData => {
        this.parsedDataFromSpec = parsedData;
      },
      error => {
        console.error('Error fetching parsed data:', error);
      }
    );
  }

  
  downloadParsedData(parsedData: any, fileName: string): void {
    const jsonData = JSON.stringify(parsedData);

    // Create a blob from the JSON string
    const blob = new Blob([jsonData], { type: 'application/json' });

    // Create a temporary link element to trigger the download
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);

    // Set the desired file name
    const downloadFileName = fileName.replace(/\.[^/.]+$/, "") + '.json';
    link.download = downloadFileName;

    // Simulate a click on the link to trigger the download
    link.click();

    // Clean up resources
    window.URL.revokeObjectURL(link.href);
  }


}
