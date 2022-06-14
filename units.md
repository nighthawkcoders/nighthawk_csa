<ul>
  {% for notebooks in site.notebooks %}
    <li>
      <a href="{{ notebooks.url }}">{{ notebooks.title }}</a>
      {{ notebooks.excerpt }}
    </li>
  {% endfor %}
</ul>
